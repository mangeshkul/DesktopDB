package pageObjectsPages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.DesktopWindow;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;

public class CommonMethodsForActivityPOS {
	private WindowsDriver driver;

	String url = "jdbc:sqlserver://RYSUN-MSSQL-02.rysun.com;" + "instanceName=SQL2016;" + "databaseName=NSCI_QA;"
			+ "encrypt=true;" + "trustServerCertificate=true;";

	private String user = "kishor";
	private String pass = "xraxjsSL9P1z";

	// Constructor
	public CommonMethodsForActivityPOS(WindowsDriver driver) {
		this.driver = driver;
	}

	public String getWindowHandleHex(String processName) throws Exception {
		String command = "powershell -command \"(Get-Process " + processName + ").MainWindowHandle.ToString('X')\"";
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		return reader.readLine();
	}

	/*
	 * public void launchApplication(String exePath) throws Exception { new
	 * ProcessBuilder(exePath).start(); Thread.sleep(8000); printAllWindows(); }
	 */

	public void launchApplication(String exePath) throws Exception {

		File exe = new File(exePath);

		if (!exe.exists()) {
			throw new RuntimeException("❌ EXE Not found: " + exePath);
		}

		System.out.println("Launching app: " + exePath);
		System.out.println("Working directory: " + exe.getParent());

		ProcessBuilder pb = new ProcessBuilder(exe.getAbsolutePath());
		pb.directory(exe.getParentFile()); // REQUIRED for most apps
		pb.start();
		Thread.sleep(8000);
		printAllWindows();
	}

	public void printAllWindows() {
		List<DesktopWindow> windows = WindowUtils.getAllWindows(true);

		for (DesktopWindow w : windows) {
			String title = w.getTitle();
			if (title != null && !title.trim().isEmpty()) {
				// System.out.println("Window: " + title +" | Handle: " + w.getHWND());
			}
		}
	}

	public Map<String, String> getGuestDataFromDB(String mCodeWithPrefix) {
		Map<String, String> guestData = new HashMap<>();
		String query = "Select * from dbo.CRMMemberDebitCredit Order By Id Desc";

		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			// pstmt.setString(1, mCodeWithPrefix);

			int count = 0;
			while (rs.next() && count < 10) {
				System.out.println("Record " + (count + 1) + ":");
				System.out.println("Id: " + rs.getString("Id"));
				System.out.println("MasterId: " + rs.getString("MasterId"));
				System.out.println("MemberId: " + rs.getString("MemberId"));
				System.out.println("InvoiceId: " + rs.getString("InvoiceId"));
				System.out.println("----------------------------");
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guestData;
	}

	public Map<String, String> getDataFromDataBase(String mCodeWithPrefix) {

		Map<String, String> guestData = new HashMap<>();

		String query = "SELECT TOP 1 CD.MasterId, TM.BillNo, CI.BillCode, CI.InvoiceNo, CD.Date, CD.MemberId, "
				+ "CD.Debit, CD.Credit, CD.DebitAmount, CD.Description " + "FROM CRMTransactionMaster TM WITH (NOLOCK) "
				+ "INNER JOIN CRMMember CM WITH (NOLOCK) ON TM.MemberId = CM.id "
				+ "INNER JOIN CRMTransactionDetail TD ON TM.Id = TD.MasterId "
				+ "    AND TD.IsDeleted = 0 AND TM.IsDeleted = 0 "
				+ "LEFT JOIN CRMMemberDebitCredit CD ON CD.MasterId = TM.Id AND CD.IsDeleted = 0 "
				+ "LEFT JOIN CRMMemberInvoice CI ON CI.Id = CD.InvoiceId " + "WHERE CM.MCodeWithPrefix = ? "
				+ "ORDER BY CD.MasterId DESC;";

		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, mCodeWithPrefix);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					guestData.put("MasterId", rs.getString("MasterId"));
					guestData.put("BillNo", rs.getString("BillNo"));
					guestData.put("Date", rs.getString("Date"));
					guestData.put("BillCode", rs.getString("BillCode"));
					guestData.put("Debit", rs.getString("Debit"));
					guestData.put("Credit", rs.getString("Credit"));
					guestData.put("DebitAmount", rs.getString("DebitAmount"));
					guestData.put("Description", rs.getString("Description"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return guestData;
	}

	public void writeJsonSimple(Map<String, String> data, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {

			writer.write("{\n");

			int count = 0;
			int size = data.size();

			for (Map.Entry<String, String> entry : data.entrySet()) {
				writer.write("  \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
				count++;
				if (count < size)
					writer.write(",");
				writer.write("\n");
			}

			writer.write("}");
			System.out.println("✅ JSON written to: " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ Failed to write JSON!");
		}
	}
	
	public String getMemberCode(String filePath, String key) {
	    JSONParser parser = new JSONParser();

	    try (FileReader reader = new FileReader(filePath)) {
	        JSONObject jsonObject = (JSONObject) parser.parse(reader);
	        return (String) jsonObject.get(key);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}


}