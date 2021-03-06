import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainPanel extends JFrame implements ChangeListener{
	
	//---------SOME VARIABLES--------------
	Connection conn=null;
	JFrame jframe;
	static int id=-1;
	static int row;
	static int selectedTab = 0;
	static int currentTab = 0;
	static String selected;
	
	ResultSet result = null;
	
	PreparedStatement state = null;
	
	//---------TABLES-----------------
	JTable brandTable = new JTable();
	JScrollPane scroller = new JScrollPane(brandTable);
	JTable laptopTable = new JTable();
	JTable laptopSelectTable = new JTable();
	JTable salesTable = new JTable();
	
	JTable laptopCriteriaTable = new JTable();
	JScrollPane laptopCriteriaScroll = new JScrollPane(laptopCriteriaTable);
	JTable saleCriteriaTable = new JTable();
	JScrollPane saleCriteriaScroll = new JScrollPane(saleCriteriaTable);
	
	JScrollPane scrollerSales = new JScrollPane(salesTable);
	
	JScrollPane scrollerLaptop = new JScrollPane(laptopTable);
	JScrollPane scrollerLaptopSale = new JScrollPane(laptopSelectTable);
	JTabbedPane tab = new JTabbedPane();
	
	
	//----------PANELS---------------
	JPanel brandConfig = new JPanel();
	JPanel laptopConfig = new JPanel();
	JPanel salePanel = new JPanel();
	JPanel searchPanel = new JPanel();
	
	//-------------------------------
	//panels for brandConfig Tab
	//upPanel for TextFields
	JPanel upPanel = new JPanel();
	//midPanel for the Buttons
	JPanel midPanel = new JPanel();
	//search panel
	JPanel searchPanelBrand = new JPanel();
	//downPanel for the results
	JPanel downPanel = new JPanel();
	JPanel blankPanel = new JPanel();
	//----------------------------
	
	//labels for brandConfig Tab
	JLabel laptopBrand = new JLabel("???????????? ?????????? ???? ??????????????: ");
	JLabel countryOfOrigin = new JLabel("?????????????? ???? ????????????????????????: ");
	JLabel searchLabelBrand = new JLabel("?????????????? ???? ??????????????: ");
	
	//TextFields for brandConfig Tab
	JTextField laptopBrandTF = new JTextField();
	JTextField countryOfOriginTF = new JTextField();
	JTextField searchByCountryTF = new JTextField();
	
	//buttons for brandConfig Tab
	JButton addBtn = new JButton("????????????");
	JButton deleteBtn = new JButton("????????????");
	JButton editBtn = new JButton("??????????????");
	JButton searchBrandBtn = new JButton("??????????");
	JButton searchBrandCancelBtn = new JButton("???????????? ??????????????????");
	
	//-----------------------------
	
	//panels for laptopConfig Tab
	//upPanel for TextFields
	JPanel upPanelLaptop = new JPanel();
	//midPanel for the Buttons
	JPanel midPanelLaptop = new JPanel();
	//downPanel for the results
	JPanel downPanelLaptop = new JPanel();
	
	//labels for laptopConfig
	JLabel brandLaptop = new JLabel("???????????? ?????????? ????????????: ");
	JLabel modelLaptop = new JLabel("???????????? ?????????? ???? ????????????: ");
	JLabel yearLaptop = new JLabel("???????????? ???????????? ???? ????????????????????????: ");
	JLabel priceLaptop = new JLabel("???????????? ???????? ???? ??????????????: ");
	JLabel commentLaptop = new JLabel("????????????????: ");
	JLabel searchLaptop = new JLabel("?????????? ???? ????????(??????): ");
	
	
	
	//----------------------------------
	//TextFields and ComboBox of laptopConfig
	static ArrayList<String> brandList = BrandDB.getBrandData(); 
	
	static String[] array = brandList.toArray(new String[brandList.size()]);
	
	//static JComboBox brandCombo = new JComboBox(array);
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(array);
	
    JComboBox<String> brandCombo = new JComboBox<>(model);
    JComboBox<String> brandCriteriaCombo = new JComboBox<>(model);
    JComboBox<String> brandCriteriaCombo2 = new JComboBox<>(model);
    
    
	JTextField modelLaptopTF = new JTextField();
	JTextField yearLapopTF = new JTextField();
	JTextField priceLaptopTF = new JTextField();
	JTextField commentTF = new JTextField();
	JTextField searchLaptopTF = new JTextField();
	
	
	//----------------------------------------------
	//Buttons for laptopConfig
	JButton addBtnLaptop = new JButton("????????????");
	JButton deleteBtnLaptop = new JButton("????????????");
	JButton editBtnLaptop = new JButton("??????????????");
	JButton searchLaptopBtn = new JButton("??????????");
	JButton searchLaptopCancelBtn = new JButton("???????????? ??????????????????");
	
	
	//------------------------------------------------
	//method which checks which tab is opened
	public void stateChanged(ChangeEvent e) {
        JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        selectedTab = tabbedPane.getSelectedIndex();
        currentTab = selectedTab;
        
    }
	
	
	//----------COMBO BOXES FOR DATE--------
	String[] months = {"01", "02","03","04","05","06","07","08","09","10","11","12"};
	String[] days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	String[] years ={"2022","2023","2024","2025","2026","2027","2028"};
	DefaultComboBoxModel<String> monthModel = new DefaultComboBoxModel<>(months);
    JComboBox<String> cbMonths = new JComboBox<>(monthModel);
    DefaultComboBoxModel<String> daysModel = new DefaultComboBoxModel<>(days);
    JComboBox<String> cbDays = new JComboBox<>(daysModel);  
    DefaultComboBoxModel<String> yearModel = new DefaultComboBoxModel<>(years);
    JComboBox<String> cbYears = new JComboBox<>(yearModel);
    
    //--------SALES LABELS AND TEXTFIELDS
    JLabel firstName = new JLabel("?????????? ?????? ???? ????????????: ");
  	JLabel lastName = new JLabel("?????????????? ???? ????????????: ");
  	JLabel salePrice = new JLabel("???????? ?????? ????????????????????: ");
	JTextField firstNameTF = new JTextField();
	JTextField lastNameTF = new JTextField();
	JTextField salePriceTF = new JTextField();
	java.util.Date dateFormatted;
	
	
	//---Search Field sale Panel
	JTextField searchField = new JTextField();
	
	
	
	
	JTextField modelCriteriaTF;
	JTextField modelCriteriaTF2;
	JTextField nameCriteriaTF;
	
	//-------------------MAIN PANEL------------------------------
	public MainPanel() {
		
		this.setSize(800,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("?????????????? ???? ?????????????? - ?????????????????????????????????????????? ????????");
		
		//brandConfig Code
		//-------------------------------------------------
		//configuration of upPanel in brandConfig
		brandConfig.setLayout(new GridLayout(4,1));
		upPanel.setLayout(new GridLayout(2,2));
		upPanel.add(laptopBrand);
		upPanel.add(laptopBrandTF);
		upPanel.add(countryOfOrigin);
		upPanel.add(countryOfOriginTF);
		brandConfig.add(upPanel);
		
		//Configuration of midPanel in brandConfig
		midPanel.setLayout(new GridLayout(3,1));
		midPanel.add(addBtn);
		midPanel.add(deleteBtn);
		midPanel.add(editBtn);
		brandConfig.add(midPanel);
		
		//buttons action listeners
		addBtn.addActionListener(new AddAction());
		deleteBtn.addActionListener(new DeleteAction());
		editBtn.addActionListener(new EditAction());
		searchBrandBtn.addActionListener(new SearchAction());
		searchBrandCancelBtn.addActionListener(new CancelSearchAction());
		
		//setting button colors
		addBtn.setBackground(Color.green);
		deleteBtn.setBackground(new Color(255,138,138));
		editBtn.setBackground(new Color(204,204,255));
		
		
		
		//Adding tabs to the mainPanel
		tab.add(brandConfig,"???????????????????????? ???? ??????????");
		tab.add(laptopConfig,"???????????????????????? ???? ??????????????");
		tab.add(salePanel,"????????????????");
		tab.add(searchPanel,"?????????????? ???? ????????????????");
		this.add(tab);
		
		//setting fontSize to bigger one
		laptopBrand.setFont(laptopBrand.getFont().deriveFont(15.0f));
		countryOfOrigin.setFont(countryOfOrigin.getFont().deriveFont(15.0f));
		
		
		//downPanel of brandConfig
		scroller.setPreferredSize(new Dimension(760,170));
		downPanel.add(scroller);
		brandTable.setModel(BrandDB.getAllData());
		brandConfig.add(downPanel);
		
		scroller.setPreferredSize(new Dimension(760,170));

		
		brandTable.addMouseListener(new TableListener());
		//-------------------------------------------------
		
		//adding search textBox and label to searchPanelBrand
		searchPanelBrand.setLayout(new GridLayout(2,2));
		searchPanelBrand.add(searchLabelBrand);
		searchPanelBrand.add(searchByCountryTF);
		searchPanelBrand.add(searchBrandBtn);
		searchPanelBrand.add(searchBrandCancelBtn);
		brandConfig.add(searchPanelBrand);
		
		
		//-----------LAPTOP CONFIG--------------
		laptopConfig.setLayout(new GridLayout(3,1));
		//setting upPanel in laptopConfig
		upPanelLaptop.setLayout(new GridLayout(5,2));
		upPanelLaptop.add(brandLaptop);
		upPanelLaptop.add(brandCombo);
		upPanelLaptop.add(modelLaptop);
		upPanelLaptop.add(modelLaptopTF);
		upPanelLaptop.add(yearLaptop);
		upPanelLaptop.add(yearLapopTF);
		upPanelLaptop.add(priceLaptop);
		upPanelLaptop.add(priceLaptopTF);
		upPanelLaptop.add(commentLaptop);
		upPanelLaptop.add(commentTF);
		laptopConfig.add(upPanelLaptop);
		
		
		
		//------------------------
		//SETTING FONTS AND COLORS
		brandLaptop.setFont(brandLaptop.getFont().deriveFont(20.0f));
		modelLaptop.setFont(modelLaptop.getFont().deriveFont(20.0f));
		yearLaptop.setFont(yearLaptop.getFont().deriveFont(20.0f));
		priceLaptop.setFont(priceLaptop.getFont().deriveFont(20.0f));
		commentLaptop.setFont(commentLaptop.getFont().deriveFont(20.0f));
		addBtnLaptop.setFont(addBtnLaptop.getFont().deriveFont(25.0f));
		editBtnLaptop.setFont(editBtnLaptop.getFont().deriveFont(25.0f));
		deleteBtnLaptop.setFont(deleteBtnLaptop.getFont().deriveFont(25.0f));
		addBtnLaptop.setBackground(Color.green);
		deleteBtnLaptop.setBackground(new Color(255,138,138));
		editBtnLaptop.setBackground(new Color(204,204,255));
		
		
		//-------SETTING MID PANEL IN LAPTOP PANEL----------
		midPanelLaptop.setLayout(new GridLayout(2,6));
		midPanelLaptop.add(addBtnLaptop);
		midPanelLaptop.add(editBtnLaptop);
		midPanelLaptop.add(deleteBtnLaptop);
		midPanelLaptop.add(searchLaptop);
		midPanelLaptop.add(searchLaptopTF);
		midPanelLaptop.add(searchLaptopBtn);
		laptopConfig.add(midPanelLaptop);
		
		
		//----SETTING DOWN PANEL IN LAPTOP PANEL----------
		downPanelLaptop.add(scrollerLaptop);
		downPanelLaptop.add(searchLaptopCancelBtn);
		laptopConfig.add(downPanelLaptop);
		
		
		//----------setting table of LAPTOPS----------
		scrollerLaptop.setPreferredSize(new Dimension(760,180));
		searchLaptop.setFont(searchLaptop.getFont().deriveFont(20.0f));
		searchLaptopBtn.setFont(searchLaptopBtn.getFont().deriveFont(20.0f));
		laptopTable.setModel(LaptopDB.getAllData());
		laptopTable.addMouseListener(new TableListener());
		
		//adding actionListeners to LAPTOP PANEL BUTTONS
		addBtnLaptop.addActionListener(new AddAction());
	    deleteBtnLaptop.addActionListener(new DeleteAction());
	    editBtnLaptop.addActionListener(new EditAction());
		searchLaptopBtn.addActionListener(new SearchAction());
		searchLaptopCancelBtn.addActionListener(new CancelSearchAction());
		
		
		//------ELEMENTS FOR salePanel------------
		//upPanel for TextFields
		JPanel upPanelSale = new JPanel();
		//datePanel for Sales
		JPanel datePanelSale = new JPanel();
		//midPanel for the Buttons
		JPanel midPanelSale = new JPanel();
		//Button Panel
		JPanel saleButtons = new JPanel();
		//search panel
		JPanel searchPanelSale = new JPanel();
		//downPanel for the results
		JPanel downPanelSale = new JPanel();
		
		//---------------upPanel Sale CONFIG-------------
		JLabel selectLaptop = new JLabel("???????????? ???????????? ???????? ???????????????? 1 ?????? ?????????? ????????: ");
		laptopSelectTable.setModel(LaptopDB.getAllData());
		selectLaptop.setFont(selectLaptop.getFont().deriveFont(20.0f));
		selectLaptop.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		upPanelSale.setLayout(new GridLayout(2,1));
		upPanelSale.add(selectLaptop);
		upPanelSale.add(scrollerLaptopSale);
		scrollerLaptopSale.setPreferredSize(new Dimension(550,100));
		salePanel.setLayout(new GridLayout(5,2));
		salePanel.add(upPanelSale);
		
		
		
		//----------------midPanel Sale--------------
		
		
		midPanelSale.setLayout(new GridLayout(4,2));
		midPanelSale.add(firstName);
		midPanelSale.add(firstNameTF);
		midPanelSale.add(lastName);
		midPanelSale.add(lastNameTF);
		midPanelSale.add(salePrice);
		midPanelSale.add(salePriceTF);
		
		salePanel.add(midPanelSale);
		
		//------SETTING DATEPICKER
		
		JLabel dateSaleLabel = new JLabel("???????? ???? ????????????????????:     ");
		JLabel day = new JLabel("??????:");
		JLabel month = new JLabel("   ??????????:");
		JLabel year = new JLabel("   ????????????:");
		//datePanelSale.setLayout(new GridLayout(3,2));
		datePanelSale.add(dateSaleLabel);
		datePanelSale.add(day);
		datePanelSale.add(cbDays);
		datePanelSale.add(month);
		datePanelSale.add(cbMonths);
		datePanelSale.add(year);
		datePanelSale.add(cbYears);
		salePanel.add(datePanelSale);
		
		
		//--------------SALE BUTTONS----------------------
		JButton addSaleBtn = new JButton("???????????? ????????????????");
		JButton editSaleBtn = new JButton("???????????????????? ????????????????");
		JButton deleteSaleBtn = new JButton("???????????????? ????????????????");
		
		saleButtons.setLayout(new GridLayout(1,3));
		saleButtons.add(addSaleBtn);
		saleButtons.add(editSaleBtn);
		saleButtons.add(deleteSaleBtn);
		addSaleBtn.setBackground(Color.green);
		deleteSaleBtn.setBackground(new Color(255,138,138));
		editSaleBtn.setBackground(new Color(204,204,255));
		addSaleBtn.setFont(addSaleBtn.getFont().deriveFont(20.0f));
		editSaleBtn.setFont(editSaleBtn.getFont().deriveFont(20.0f));
		deleteSaleBtn.setFont(deleteSaleBtn.getFont().deriveFont(20.0f));
		datePanelSale.add(saleButtons);
		addSaleBtn.addActionListener(new AddAction());
		editSaleBtn.addActionListener(new EditAction());
		deleteSaleBtn.addActionListener(new DeleteAction());
		//--------------SALES TABLE----------------
		scrollerSales.setPreferredSize(new Dimension(760,140));
		salesTable.setModel(SaleDB.getAllData());
		downPanelSale.add(scrollerSales);
		salePanel.add(downPanelSale);
		
		
		//-----------SEARCH PART
        JLabel searchByName = new JLabel("?????????? ???? ?????????? ?????? ???? ??????????????:");
		
		JButton searchByNameBtn = new JButton("??????????");
		JButton cancelNameSearch = new JButton("???????????? ??????????????????");
		searchByNameBtn.addActionListener(new SearchAction());
		cancelNameSearch.addActionListener(new CancelSearchAction());
		
		searchPanelSale.setLayout(new GridLayout(2,2));
		searchPanelSale.add(searchByName);
		searchPanelSale.add(searchField);
		searchPanelSale.add(searchByNameBtn);
		searchPanelSale.add(cancelNameSearch);
		salePanel.add(searchPanelSale);
	    
		
		//--Search by 2 criteria panel
		searchPanel.setLayout(new GridLayout(6,1));
		JPanel headerPanel = new JPanel();
		JPanel upPanelCriteria = new JPanel();
		JPanel downPanelCriteria = new JPanel();
		upPanelCriteria.setLayout(new GridLayout(3,2));
		downPanelCriteria.setLayout(new GridLayout(4,2));
		JLabel laptopCriteriaLabel = new JLabel("          ?????????????? ?? ?????????????? ?? ??????????????:");
		JLabel saleCriteriaLabel = new JLabel("          ?????????????? ?? ?????????????? ?? ????????????????:");
		
		JLabel brandCriteriaLabel = new JLabel("???????????? ?????????? ???? ??????????????:");
		JLabel modelCriteriaLabel = new JLabel("???????????? ?????????? ???? ??????????????:");
		modelCriteriaTF = new JTextField();
		JButton laptopSearchBtn = new JButton("?????????? ?? ?????????????? ??????????????");
		JButton laptopCriteriaCancelBtn = new JButton("???????????? ???? ??????????????");
		//---------------------
		JLabel brandCriteriaLabel2 = new JLabel("???????????? ?????????? ???? ??????????????:");
		JLabel modelCriteriaLabel2 = new JLabel("???????????? ?????????? ???? ??????????????:");
		modelCriteriaTF2 = new JTextField();
		JLabel nameCriteriaLabel = new JLabel("???????????? ?????????????? ?????? ???? ??????????????:");
		nameCriteriaTF = new JTextField();
		JButton saleCriteriaSearchBtn = new JButton("?????????? ?? ?????????????? ????????????????");
		JButton saleCriteriaCancelBtn = new JButton("???????????? ???? ??????????????");
		
		laptopCriteriaLabel.setFont(laptopCriteriaLabel.getFont().deriveFont(35.0f));
		saleCriteriaLabel.setFont(saleCriteriaLabel.getFont().deriveFont(35.0f));
		brandCriteriaLabel.setFont(brandCriteriaLabel.getFont().deriveFont(15.0f));
		modelCriteriaLabel.setFont(modelCriteriaLabel.getFont().deriveFont(15.0f));
		
		headerPanel.add(laptopCriteriaLabel);
		
		searchPanel.add(laptopCriteriaLabel);
		
		upPanelCriteria.add(brandCriteriaLabel);
		upPanelCriteria.add(brandCriteriaCombo);
		upPanelCriteria.add(modelCriteriaLabel);
		upPanelCriteria.add(modelCriteriaTF);
		upPanelCriteria.add(laptopSearchBtn);
		upPanelCriteria.add(laptopCriteriaCancelBtn);
		laptopCriteriaTable.setModel(LaptopDB.getAllData());
		saleCriteriaTable.setModel(SaleDB.getAllData());
		downPanelCriteria.add(brandCriteriaLabel2);	
		downPanelCriteria.add(brandCriteriaCombo2);
		downPanelCriteria.add(modelCriteriaLabel2);
		downPanelCriteria.add(modelCriteriaTF2);
		downPanelCriteria.add(nameCriteriaLabel);
		downPanelCriteria.add(nameCriteriaTF);
		downPanelCriteria.add(saleCriteriaSearchBtn);
		downPanelCriteria.add(saleCriteriaCancelBtn);
		laptopSearchBtn.setBackground(Color.LIGHT_GRAY);
		laptopCriteriaCancelBtn.setBackground(new Color(255,138,138));
		saleCriteriaSearchBtn.setBackground(new Color(44,204,255));
		saleCriteriaCancelBtn.setBackground(new Color(104,244,55));
		laptopSearchBtn.addActionListener(new SearchLaptopCriteriaAction());
		laptopCriteriaCancelBtn.addActionListener(new SearchLaptopCriteriaCancelAction());
		saleCriteriaSearchBtn.addActionListener(new SearchSaleCriteriaAction());
		saleCriteriaCancelBtn.addActionListener(new SearchSaleCriteriaCancelAction());
		searchPanel.add(upPanelCriteria);
		searchPanel.add(laptopCriteriaScroll);
		searchPanel.add(saleCriteriaLabel);
		searchPanel.add(downPanelCriteria);
		searchPanel.add(saleCriteriaScroll);
		laptopSelectTable.addMouseListener(new TableListener());
		salesTable.addMouseListener(new TableListener());
		
		tab.setBackground(new Color(205,150,210));
		searchPanel.setBackground(new Color(225,150,240));
		tab.addChangeListener(this);
		this.setVisible(true);
	}
	//----------------------------------------
	
	
	
	
	//clears text fields in first form
	public void clearFirstForm() {
		laptopBrandTF.setText("");
		countryOfOriginTF.setText("");
		
	}
	
	//clears text fields in the second form
	public void clearSecondForm() {
		
		modelLaptopTF.setText("");
		yearLapopTF.setText("");
		priceLaptopTF.setText("");
		commentTF.setText("");
	}
	
	public void clearThirdForm() {
		firstNameTF.setText("");
		lastNameTF.setText("");
		salePriceTF.setText("");
	}
	
	
	
	//------------------------------------------
	//class with addBtn Action
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(currentTab == 0) {
				
				
				conn = BrandDB.getConnection();
				
				String sql = "insert into BRANDS values(DEFAULT,?,?)";
				try {
					state = conn.prepareStatement(sql);
					state.setString(1, laptopBrandTF.getText());
					state.setString(2, countryOfOriginTF.getText());
					
					state.execute();
					//
					brandTable.setModel(BrandDB.getAllData());
					
					//getting all the brands again
					array = brandList.toArray(new String[brandList.size()]);
					model.addElement(laptopBrandTF.getText());
				    brandCombo.setSelectedItem(laptopBrandTF.getText());
				    brandCriteriaCombo.setSelectedItem(laptopBrandTF.getText());
				    brandCriteriaCombo2.setSelectedItem(laptopBrandTF.getText());
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						//brandCombo = new JComboBox();
						
						state.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				clearFirstForm();
				
			}
			
			else if(currentTab == 1) {
				conn = LaptopDB.getConnection();
				String sql = "insert into LAPTOPS values(DEFAULT,?,?,?,?,?)";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, LaptopDB.getBrandData(brandCombo.getSelectedItem().toString())); //brandCombo.getSelectedItem().toString()
					state.setString(2, modelLaptopTF.getText());
					state.setInt(3, Integer.parseInt(yearLapopTF.getText()));
					state.setFloat(4, Float.parseFloat(priceLaptopTF.getText()));
					state.setString(5, commentTF.getText());
					
					state.execute();
					laptopTable.setModel(LaptopDB.getAllData());
					laptopSelectTable.setModel(LaptopDB.getAllData());
					laptopCriteriaTable.setModel(LaptopDB.getAllData());

					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
			
						
						state.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				clearSecondForm();
			}
			else if(currentTab == 2) {
				conn = SaleDB.getConnection();
				String sql = "insert into SALES values(DEFAULT,?,?,?,?,?,?)";
				String dateString = cbYears.getSelectedItem().toString() + "-" + cbMonths.getSelectedItem().toString() + "-" + cbDays.getSelectedItem().toString();
				java.util.Date utilDate = null;
				try {
					utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				
				float difference = Float.parseFloat(salePriceTF.getText()) - Float.parseFloat(laptopSelectTable.getValueAt(row, 4).toString());
				
				
				try {
					
					state = conn.prepareStatement(sql);
					state.setInt(1, Integer.parseInt(laptopSelectTable.getValueAt(row, 0).toString()));
					state.setDate(2, sqlDate);
					state.setString(3, firstNameTF.getText());
					state.setString(4, lastNameTF.getText());
					state.setFloat(5, Float.parseFloat(salePriceTF.getText()));
					state.setFloat(6, difference);
					
					
					
					state.execute();
					salesTable.setModel(SaleDB.getAllData());
					saleCriteriaTable.setModel(SaleDB.getAllData());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {

						
						state.close();
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				clearThirdForm();
			}
			
			//
		}
		
	}
	
	
	
	//-------------------------------------------
	//class with deleteBtn Action
	class DeleteAction implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentTab == 0) {
				// TODO Auto-generated method stub
				conn = BrandDB.getConnection();
				String sql = "DELETE FROM BRANDS WHERE ID=?";
				try {
					
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					
					state.execute();
					
					
					brandTable.setModel(BrandDB.getAllData());
					array = brandList.toArray(new String[brandList.size()]);
					model.removeElement(selected);
					
					id = -1;
				    
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jframe,"?????????????????????? ?????????????? ???? ???????????????? ??????????.?????? ???????????? ???? ???????????????? ??????????????, ???????????????? ?????????? ???????????? ?????????????? ???? ???????????????? ??????????!","????????????????!",JOptionPane.WARNING_MESSAGE);
					//e1.printStackTrace();
				}
				
			}
			else if(currentTab == 1) {
				// TODO Auto-generated method stub
				conn = BrandDB.getConnection();
				String sql = "DELETE FROM LAPTOPS WHERE LAPTOPID=?";
				try {
					
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					
					
					laptopTable.setModel(LaptopDB.getAllData());
					laptopSelectTable.setModel(LaptopDB.getAllData());
					laptopCriteriaTable.setModel(LaptopDB.getAllData());
					id = -1;
				    
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(jframe,"?????????????????????? ???????????? ???? ???????????????? ???? ???????????? ?????? ???????????????? ??????????????????, ???????????????? ?????????? ???????????? ???? ????????????????????,???? ???? ???????????????? ??????????????!","????????????????!",JOptionPane.WARNING_MESSAGE);
					//e1.printStackTrace();
				}
			}
			else if(currentTab == 2) {
				// TODO Auto-generated method stub
				conn = SaleDB.getConnection();
				String sql = "DELETE FROM SALES WHERE SALEID=?";
				try {
					
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					
					
					salesTable.setModel(SaleDB.getAllData());
					saleCriteriaTable.setModel(SaleDB.getAllData());
					id = -1;
				    
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	//-------------------------------------------------
	//class with editBtn Action
	class EditAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(currentTab == 0) {
				// TODO Auto-generated method stub
				conn = BrandDB.getConnection();
				String sql = "UPDATE BRANDS SET BRAND = \'" + laptopBrandTF.getText() + "\', COUNTRY = \'"  + countryOfOriginTF.getText() + "\' WHERE ID=?;";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					id = -1;
					brandTable.setModel(BrandDB.getAllData());
					laptopTable.setModel(LaptopDB.getAllData());
					salesTable.setModel(SaleDB.getAllData());
					laptopSelectTable.setModel(LaptopDB.getAllData());
					laptopCriteriaTable.setModel(LaptopDB.getAllData());
					saleCriteriaTable.setModel(SaleDB.getAllData());
					array = brandList.toArray(new String[brandList.size()]);
					//int index = brandList.indexOf(selected);
					model.removeElement(selected);
					model.addElement(laptopBrandTF.getText());
				    brandCombo.setSelectedItem(laptopBrandTF.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clearFirstForm();
			}
			else if(currentTab == 1) {
				// TODO Auto-generated method stub
				conn = BrandDB.getConnection();
				String sql = "UPDATE LAPTOPS SET BRANDID = \'" + LaptopDB.getBrandData(brandCombo.getSelectedItem().toString()) + "\', MODEL = \'"  + modelLaptopTF.getText() + "\', YEAROFPRODUCTION = \'"  + yearLapopTF.getText() + "\', PRICE = \'"  + priceLaptopTF.getText() + "\', COMMENT = \'"  + commentTF.getText() + "\' WHERE LAPTOPID=?;";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					id = -1;
					laptopTable.setModel(LaptopDB.getAllData());
					laptopSelectTable.setModel(LaptopDB.getAllData());
					laptopCriteriaTable.setModel(LaptopDB.getAllData());
					saleCriteriaTable.setModel(SaleDB.getAllData());
					//int index = brandList.indexOf(selected);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clearSecondForm();
			}
			else if(currentTab == 2) {
				conn = SaleDB.getConnection();
				String dateString = cbYears.getSelectedItem().toString() + "-" + cbMonths.getSelectedItem().toString() + "-" + cbDays.getSelectedItem().toString();
				java.util.Date utilDate = null;
				try {
					utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				
				float difference = Math.abs(Float.parseFloat(salesTable.getValueAt(row, 7).toString())) - Float.parseFloat(salePriceTF.getText()) + Float.parseFloat(salesTable.getValueAt(row, 6).toString());
				String sql = "UPDATE SALES SET FIRSTNAME = \'"+ firstNameTF.getText() + "\', LASTNAME = \'" + lastNameTF.getText() + "\', SALEDATE = \'" + sqlDate + "\',SALEPRICE = " +Float.parseFloat(salePriceTF.getText()) +" , DIFFERENCE = "+ difference + " WHERE SALEID=?;";
				try {
					state = conn.prepareStatement(sql);
					state.setInt(1, id);
					state.execute();
					id = -1;
					salesTable.setModel(SaleDB.getAllData());
					saleCriteriaTable.setModel(SaleDB.getAllData());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clearThirdForm();
			}
			
		}
	}
	
	
	//-------------CLASS WITH SEARCHBTN ACTION---------------------------
    class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(currentTab == 0) {
				conn = BrandDB.getConnection();
				String sql = "SELECT * FROM BRANDS WHERE COUNTRY = \'" + searchByCountryTF.getText() + "\';";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					brandTable.setModel(BrandDB.getSearchData(searchByCountryTF.getText()));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(currentTab == 1) {
				conn = LaptopDB.getConnection();
				String sql = "SELECT * FROM LAPTOPS WHERE PRICE > " + searchLaptopTF.getText();
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					laptopTable.setModel(LaptopDB.getSearchData(searchLaptopTF.getText()));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(currentTab == 2) {
				conn = SaleDB.getConnection();
				String sql = "SELECT * FROM SALES WHERE FIRSTNAME = \'" + searchField.getText() + "\'";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					salesTable.setModel(SaleDB.getSearchData(searchField.getText()));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    	
    }
    
    
    
    //----------------CANCEL SEARCHBTN CLASS------------------------
    class CancelSearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(currentTab == 0) {
				conn = BrandDB.getConnection();
				String sql = "SELECT * FROM BRANDS;";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					brandTable.setModel(BrandDB.getAllData());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(currentTab == 1) {
				conn = LaptopDB.getConnection();
				String sql = "SELECT * FROM LAPTOPS;";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					laptopTable.setModel(LaptopDB.getAllData());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(currentTab == 2) {
				conn = SaleDB.getConnection();
				String sql = "SELECT * FROM SALES;";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					salesTable.setModel(SaleDB.getAllData());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
    	
    }
    
    
    class SearchLaptopCriteriaAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				conn = LaptopDB.getConnection();
				//String sql = "SELECT * FROM BRANDS WHERE COUNTRY = \'" + searchByCountryTF.getText() + "\';";
				
				String sql = "SELECT LAPTOPS.LAPTOPID,BRANDS.BRAND,LAPTOPS.MODEL,LAPTOPS.YEAROFPRODUCTION,LAPTOPS.PRICE,LAPTOPS.COMMENT\r\n"
						+ "FROM LAPTOPS JOIN BRANDS \r\n"
						+ "ON LAPTOPS.BRANDID = BRANDS.ID\r\n"
						+  " WHERE BRANDS.BRAND = \'" + brandCriteriaCombo.getSelectedItem().toString() + "\' AND LAPTOPS.MODEL = \'" + modelCriteriaTF.getText() + "\'"
							+ " ORDER BY LAPTOPS.MODEL";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					laptopCriteriaTable.setModel(LaptopDB.getSearchCriteriaData(brandCriteriaCombo.getSelectedItem().toString(),modelCriteriaTF.getText()));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
    	
    }
    
    class SearchLaptopCriteriaCancelAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				conn = LaptopDB.getConnection();
				//String sql = "SELECT * FROM BRANDS WHERE COUNTRY = \'" + searchByCountryTF.getText() + "\';";
				
				String sql = "SELECT * FROM LAPTOPS";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					laptopCriteriaTable.setModel(LaptopDB.getAllData());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
    	
    }
    
    
    
    class SearchSaleCriteriaAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				conn = LaptopDB.getConnection();
				//String sql = "SELECT * FROM BRANDS WHERE COUNTRY = \'" + searchByCountryTF.getText() + "\';";
				
				String sql = "SELECT S.SALEID,B.BRAND,"
						+ "L.MODEL,S.SALEDATE,S.FIRSTNAME,"
						+ "S.LASTNAME,S.SALEPRICE,"
						+ "S.DIFFERENCE "
						+ "FROM SALES S JOIN LAPTOPS L "
						+ "ON S.LAPTOPID = L.LAPTOPID "
						+ "JOIN BRANDS B "
						+ "ON L.BRANDID = B.ID WHERE S.FIRSTNAME = \'" + nameCriteriaTF.getText() + "\' AND L.MODEL = \'" + modelCriteriaTF2.getText() + "\' AND B.BRAND = \'" + brandCriteriaCombo2.getSelectedItem().toString() + "\'";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					saleCriteriaTable.setModel(SaleDB.getSearchCriteriaData(brandCriteriaCombo2.getSelectedItem().toString(),modelCriteriaTF2.getText(),nameCriteriaTF.getText()));
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
    	
    }
    
    class SearchSaleCriteriaCancelAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				conn = SaleDB.getConnection();
				//String sql = "SELECT * FROM BRANDS WHERE COUNTRY = \'" + searchByCountryTF.getText() + "\';";
				
				String sql = "SELECT * FROM SALES";
				
				try {
					state = conn.prepareStatement(sql);
					state.execute();	
					saleCriteriaTable.setModel(SaleDB.getAllData());
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
    	
    }
    
    
	
    //-------TABLELISTENER FOR MOUSE COMMANDS-------------

	class TableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(currentTab == 0) {
				row = brandTable.getSelectedRow();
				selected = (String) brandTable.getValueAt(row, 1);
				id = Integer.parseInt(brandTable.getValueAt(row, 0).toString());
			}
			else if(currentTab == 1) {
				//continue from here
				row = laptopTable.getSelectedRow();
				selected = (String)laptopTable.getValueAt(row, 1);
				
				id = Integer.parseInt(laptopTable.getValueAt(row, 0).toString());
				
			}
			else if(currentTab == 2) {
				try {
					
					row = salesTable.getSelectedRow();
					id = Integer.parseInt(salesTable.getValueAt(row, 0).toString());
				}
				catch(Exception e1){
					row = laptopSelectTable.getSelectedRow();
					selected = (String)laptopSelectTable.getValueAt(row, 1);
					
					id = Integer.parseInt(laptopTable.getValueAt(row, 0).toString());
					
					
				}
				
			}
			
			
			
			if(e.getClickCount() == 2) {
			    if(currentTab == 0) {
			    	laptopBrandTF.setText(brandTable.getValueAt(row, 1).toString());
					countryOfOriginTF.setText(brandTable.getValueAt(row, 2).toString());
			    }
			    else if(currentTab == 1) {
			    	brandCombo.setSelectedItem(laptopTable.getValueAt(row, 1));
			    	modelLaptopTF.setText(laptopTable.getValueAt(row, 2).toString());
			    	yearLapopTF.setText(laptopTable.getValueAt(row, 3).toString());
			    	priceLaptopTF.setText(laptopTable.getValueAt(row, 4).toString());
			    	commentTF.setText(laptopTable.getValueAt(row, 5).toString());
			    }
			    else if(currentTab == 2) {
			    	row = salesTable.getSelectedRow();
			    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    	selected = dateFormat.format(salesTable.getValueAt(row, 3));
			    	String[] parts = selected.split("-");
			    	cbYears.setSelectedItem(parts[0]);
			    	cbMonths.setSelectedItem(parts[1]);
			    	cbDays.setSelectedItem(parts[2]);
			    	firstNameTF.setText(salesTable.getValueAt(row, 4).toString());
			    	lastNameTF.setText(salesTable.getValueAt(row, 5).toString());
			    	salePriceTF.setText(salesTable.getValueAt(row, 6).toString());
			    }
			    
			    
			}
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}