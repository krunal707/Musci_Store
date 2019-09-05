import java.sql.*;
import java.util.*;

public class Music_Store{
	public static void main(String args[]){

/* the main functions asks the user for input for the function to implement */		
		System.out.println("Please select on of the option from below:");
		System.out.println("1. Obtain Album title(s) based on Artist Name");
		System.out.println("2. Purchase History for a Customer");
		System.out.println("3. Update track price - individual");
		

		Scanner scanner = new Scanner(System.in);
		int user_choice = scanner.nextInt();
	    
	    switch (user_choice)
	    {
	    case 1:
	    	albumtitle_by_artistname();
	    	break;
	    case 2:
	    	purchase_history_customer();
	    	break;
	    case 3:
	    	update_trackprice_individual();
	    	break;
	    default:
	    	System.out.println("Incorrect choice");
	    	break;
	    }

	}


	public static void albumtitle_by_artistname(){


		/* This function allows user to get Album Titles based on the Artist name*/


		System.out.println("This operation finds albums by artist.");
		Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter artist's name: ");   // getting user input for artist name
        String artist = scanner.nextLine();
        // User enter the artist name which needs to be searched.

		Connection c = null;
		Statement stmt = null;
		// Connecting database

		try{

			Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt = c.createStatement();
                

                // Query to obtain Album Titles based on Astist Name.
                ResultSet rs = stmt.executeQuery("SELECT A.AlbumId, A.Title,"
                        + " AT.ArtistID FROM ALBUM A, Artist AT WHERE AT.ArtistId = A."
                        + "ArtistId AND AT.Name = \"" + artist + "\";");
                //checks if there are no Albums based on the Artist name
                if(!(rs.next())){


        		System.out.println("\nNo Album found based on your input.");
        	}
        		else{
        			do{

        				int artistid = rs.getInt("artistid");
                        int albumid = rs.getInt("albumid");
                        String title = rs.getString("title");
                        System.out.println("The output for your query is");
                        System.out.println("\nArtistId = " + artistid);
                        System.out.println("AlbumId = " + albumid);
                        System.out.println("Title = " + title + "\n");

        			}while(rs.next());
        		}

        	rs.close();
			stmt.close();
			c.close();
        	}


		catch(Exception e){

			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);

		}

		System.out.println("\nOperation done successfully");

	}



	public static void purchase_history_customer(){


		/* This function allows user to get Purchase History for a Customer based on Customer ID*/

		System.out.println("This operation finds information by Customer ID.");
		Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter customer id:");    // getting user input for customer id
        String cid = scanner.nextLine();


		Connection c = null;
		Statement stmt = null;
		// Connecting database

		try{

			Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");
                stmt = c.createStatement();


                // Query to obtain purchase history of the customer
                 ResultSet rs = stmt.executeQuery("SELECT t.name, a.Title,"
                        + " i1.Quantity, i.InvoiceDate FROM Album a, Invoice i,"
                        + " InvoiceLine i1, Track t WHERE t.TrackId"
                        + " = i1.TrackID AND i.InvoiceId = i1.InvoiceId "
                        + "AND i.CustomerId = " + cid + ";");


                 if(!(rs.next())){
                 //checks if the query returns null

        		System.out.println("\nNo Purchase History for Customer found based on your input.");
        	}		
        		else{
        			do{

						String title = rs.getString("title");
						int quantity = rs.getInt("quantity");
						String trackname = rs.getString("name");
						String invoicedate = rs.getString("invoicedate");

                       System.out.println("\nAlbum Title = " + title);
                        System.out.println("Track Name = " + trackname);
                        System.out.println("Quantity = " + quantity);
                        System.out.println("Date = " + invoicedate + "\n");

        			}while(rs.next());
        		}

			rs.close();
			stmt.close();
			c.close();



		}
		catch(Exception e){

			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);

		}

		System.out.println("\nOperation done successfully");

	}


	public static void update_trackprice_individual(){

		/* This function allows user to update the unitprice of the track*/


		System.out.println("This operation updates the individual track price.");
		Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the Track ID:"); // getting user input for track id
        Double tid = scanner.nextDouble();


		Connection c = null;
		Statement stmt = null;
		// Connecting database

		try{

			Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();




                 ResultSet rs = stmt.executeQuery("SELECT i.unitprice "
                        + " FROM Track t, InvoiceLine i"
                        + " WHERE t.TrackID = i.TrackId and t.TrackId = " + tid + ";");


            	if(!(rs.next())){
            			 //checks if the query returns null

        				System.out.println("\nNo Album found based on your input.");
        		}else{
        			do{

        				double unitprice = rs.getDouble("unitprice");

                       System.out.println("\nThe unit price is: " + unitprice + ", for Track Id:" + tid);

                       System.out.println("Enter the new unitprice:");
                       Double new_unitprice = scanner.nextDouble();

                       String sql = "UPDATE TRACK set UNITPRICE ="
                                + " '" + new_unitprice + "' where TRACKID = " + tid + ";" ;

                        stmt.executeUpdate(sql);
      					c.commit();

                       System.out.println("New Unit price is " + new_unitprice );


        			}while(rs.next());

        	}

			rs.close();
			stmt.close();
			c.close();
			// closing connection

		}
		catch(Exception e){

			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);

		}

		System.out.println("\nUnitprice updated successfully");

	}
}