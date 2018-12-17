package PsqlAndJava;
import java.sql.*;
import java.util.Scanner;
public final class Teacher{
	static java.sql.Connection con=null;
	static java.sql.Statement s=null;
	static java.sql.ResultSet rs=null;
	static java.sql.PreparedStatement pst=null;    
	static Scanner input=null;
	static boolean isexisttid=false;
	static private int tid;
            static private String name,placeofbirth;
	public Teacher() throws SQLException, ClassNotFoundException
	{
               tid=0;
               name="";
               placeofbirth="";
	       connect();
	}
	
	public void settid(int tid)
	{	Teacher.tid=tid;}
	public void setname(String name)
	{
		Teacher.name=name;
	}
	
	public void setplaceofbirth(String placeofbirth)
	{
		Teacher.placeofbirth=placeofbirth;}
	public void setinfo(int tid,String name,String placeofbirth)
	{
		Teacher.tid=tid;
		Teacher.name=name;
		Teacher.placeofbirth=placeofbirth;
	}
	
	public int gettid(int tid)
	{
		return tid;
	}
	
	public String getname(String name)
	{
		return name;
	}
	public String getplaceOfBirth(String placeOfBirth)
	{
		return placeOfBirth;
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {	
		 Teacher s=new Teacher();
                 input=new Scanner(System.in);
                 int choice ;
                 
		 s. menu();	    
         choice=input.nextInt();
		 while(choice !=0)
		 {	 
			    
				switch(choice)
				{
				
		       case 1 :            s.listTeacher ();   
					break;
		       case 2 :               s.insertTeacher ();
					break;	
		        case 3 :	 System.out.println("Hoca kodu giriniz?");
		        	              Teacher.tid=input.nextInt();
					s.deleteTeacher(tid);
					break;			
		        case 4 :     System.out.println("Hoca kodu giriniz? ");
		        	              Teacher.tid=input.nextInt();
			 		s.updateTeacher (tid);
					break;
		        case 5 :   System.exit(0);}	
		         s. menu();	    
		         choice=input.nextInt();
		 }	} 

	
	private void updateTeacher(int tid) throws SQLException 
	{
		String sql;
		System.out.println("Yeni hoca adi giriniz?:");
		name=input.nextLine();
                name=input.nextLine();
		System.out.println("Yeni hoca doğum yeri giriniz?");
		placeofbirth =input.nextLine();
		
		if (searchTeacher (tid)==true)
		{
		    sql = "UPDATE \"Teacher\" SET name = '"+name+"', placeOfBirth = '"+ placeofbirth +"' WHERE tid = "+tid;	
			s = con.createStatement();
			s.executeUpdate(sql);
			System.out.println("Hoca güncellendi.");
		}
		else
		{	
		   System.out.println("Bu koda sahip bir hoca olmadigi icin hoca güncellenemedi.");
		
		}
		
	}

	private void deleteTeacher(int tid) throws SQLException 
	{
		
		if (searchTeacher(tid)==true)
		{
		String  sql = "DELETE FROM \"Teacher\" WHERE tid ="+tid;	
				s = con.createStatement();
				s.executeUpdate(sql);
				System.out.println("Hoca silindi.");			
		}
		else
		{
			System.out.println("Bu koda sahip bir hoca olmadigi icin hoca silinemiyor.");
		}	}
public  void menu()
	{
		
		System.out.println("Ana Menü");
		System.out.println("1-listele ");
		System.out.println("2-ekle ");
		System.out.println("3-sil");
		System.out.println("4-güncelle");
		System.out.println("0-cikis");
		System.out.println("Secim yapiniz?");
		
	}
	public  void connect() throws SQLException, ClassNotFoundException
	{
            
   Class.forName("org.postgresql.Driver");

  con=DriverManager.getConnection( "mongodb://root:1234<PASSWORD>@mongodb-shard-00-00-qtcrd.mongodb.net:27017,mongodb-shard-00-01-qtcrd.mongodb.net:27017,mongodb-shard-00-02-qtcrd.mongodb.net:27017/test?ssl=true&replicaSet=mongoDB-shard-0&authSource=admin&retryWrites=true");		   
	 System.out.println("connected to database..");	
		
	
	}
	
	public  void listTeacher() throws SQLException
	{		
	        	
			    System.out.println("Öğretmenler : ");
				String sql = "select * from \"Teacher\" order by tid";		
				s = con.createStatement();
				rs=s.executeQuery(sql);
				
				while(rs.next())
				{					 				
					for(int i=1;i<=3;i++)
					{
						System.out.print(rs.getString(i)+" " );						
					}					
					     System.out.println( );
				}		
		
	}
	
	public   void insertTeacher() throws SQLException
	{
			input=new Scanner (System.in);
			System.out.println("Hoca kodu giriniz?");
			tid=input.nextInt();			
			System.out.println("Hoca adı giriniz? ");
			name=input.nextLine();
			name=input.nextLine();
			System.out.println("Hoca doğum yeri giriniz ?");
			placeofbirth=input.nextLine();
			
		 
		if(searchTeacher(tid)==false)
		
		{  String sql = "insert into \"Teacher\" values ("+tid+", '"+name+"','"+placeofbirth+"')";		
				s = con.createStatement();
				s.executeUpdate(sql);				
				System.out.println("Hoca oluşturuldu.");
		} 
		else
		{          isexisttid= true;
			System.out.println("Ayni koda sahip bir kayit oldugu için hoca olusturulamadi. ");
		    insertTeacher(); }}
	
	public boolean searchTeacher(int tid) throws SQLException
	{
		    boolean isexist=false;				    
	        s = con.createStatement();
		    rs=s.executeQuery("select * from \"Teacher\" where tid="+tid+"");	
			while(rs.next())
			{
				for(int i=1;i<=3;i++)
				{
					System.out.print(rs.getString(i)+" " );
					isexist=true;}
			}
			return isexist;	    	}
}
