<h1> CSC1035: Assessment 3 - Team 53's Project </h1>

<h3> Synopsis </h3>

This is an Electronic Point of Sale (EPOS) program which allows for a count of available stock, allows for customer  
transaction of stock, including cost of the product, the amount of money given by the customer and the change  
given back to the client. This produces a receipt of the transaction and includes a way of updating  
stock outside of a transaction.

<h3> How To Use - Entry Point - Main Menu </h3>
To run this program simply run the "Main" class. Upon running the program you will be prompted with:  

        --------[Main Menu]--------
        Select an Option Below
        1 - Open [Customer Transaction] Menu
        2 - Open [Modify Stock] Menu
        3 - Open [Output] Menu
        4 - Insert Data From CSV
        5 - View Count of Available Stock
        Enter choice: 

To select an option presented simply type the number corresponding to the command and press enter.
<ul>
<li>Option <b>1</b>, this begins and opens a <b>customer transaction menu.</b></li>
<li>Option <b>2</b>, this opens the <b>modify stock menu</b> which presents the user with 7 more options.  </li> 
<li>Option <b>3</b>, this opens the <b>modify stock menu</b> which presents the user with 4 more options.   </li> 
<li>Option <b>4</b>, allows the user to specify a directory and filename of stock data held in a CSV file, the contents  
                     held in the specified CSV file is inserted into the database.  </li> 
<li>Option <b>5</b>, displays all current stock items and their stock quantities. </li>                 
</ul>
<h3> How To Use - Modify Stock Menu </h3>

        --------[Modify Stock Menu]--------
        1 - [Insert] Stock Item To Database
        2 - [Set] Stock Of An Item
        3 - [Increase] Stock Of An Item
        4 - [Decrease] Stock Of An Item
        5 - [Delete] Stock Item From Database
        6 - View Database Content's
        7 - Back
To select an option presented simply type the number corresponding to the command and press enter.
<ul> 
<li>Option <b>1</b> - Type in each attribute for an item and then insert it into the database</li>
<li>Option <b>2</b> - Input an item's name and new stock value, the stock value of the item will be set to the value passed</li>
<li>Option <b>3</b> - Input an item's name and value, the stock value of the specified item will increase by the value passed</li>
<li>Option <b>4</b> - Input an item's name and value, the stock value of the specified item will decrease by the value passed</li>
<li>Option <b>5</b> - Input an item's name, the item will be permanently deleted from the database</li>
<li>Option <b>6</b> - Outputs the contents of the database to an ASCII table - to check if deletion occured</li>
<li>Option <b>7</b> - Return the user back to the main menu</li>
</ul>

<h3> How To Use - Customer Transaction Menu </h3>

        [Customer Transaction Menu ~ Transaction: In-Progress]
        1 - Buy Another Item
        2 - View Shopping List
        3 - Remove an Item
        4 - Cancel Transaction
        5 - Finish Transaction
        [Search/Navigation Options]
        6 - View Current Stock
        7 - Search an Item
To select an option presented simply type the number corresponding to the command and press enter.
<ul>
<li>Option <b>1</b> - The user must pass an item name and quantity if both are valid then the item is added to the shopping list</li>
<li>Option <b>2</b> - The current collection of items being bought; their name, quantity and price are outputted in an ASCII table</li>
<li>Option <b>3</b> - The user inputs an item name to remove from the current shopping list</li>
<li>Option <b>4</b> - The current transaction is cancelled - the shopping list is cleared and no other processes take place</li>
<li>Option <b>5</b> - The user inputs the money given for the transaction - the database is modified accordingly</li>
<li>Option <b>6</b> - All item names, stock and price are outputted in an ASCII table </li>
<li>Option <b>7</b> - The user enters an item name, if it exists it's current stock and price are returned</li>
</ul>
        
<h3> How To Use - Output Menu </h3>
        
        --------[Output Menu]--------
        Select an Option Below
        1 - Output Database Contents to .csv file
        2 - Output Database Contents to .txt file
        3 - View Database Content's
        4 - Back
        Enter choice: 
To select an option presented simply type the number corresponding to the command and press enter.        
<ul>
<li>Option <b>1</b> - The current content's held in the database is outputted to a CSV file in the top-level directory of this project</li>
<li>Option <b>2</b> - The contents held in the database is outputted to a text file, where the directory and filename are customizable</li>
<li>Option <b>3</b> - Outputs the contents of the database to an ASCII table</li>
<li>Option <b>4</b> - Return the user back to the main menu</li>
</ul>

<h3> Database Design </h3>

Our database is composed of 3 tables, Stock, ItemSale, and Transaction. Stock records each item's name, category,  
perishable, cost, price and sell-price. The ItemSale table records each item bought in a transaction, the quantity and  
and total price, along with it's transaction ID in order to indicate which item belongs to which transaction. Each  
item in a transaction within the table ItemSale will be identified by pointing to the item via it's stockID and then  
identifying the transaction the item belongs to is identified by the transaction ID. The transaction table keeps track  
of each transaction's total-price, money given for the transaction, and the change given. 3 Tables were used as many  
many items can be sold and a sale can contain many items.

<h3> License </h3>

See the [LICENSE.md](LICENSE.md) file for license details.

<h3> Built With </h3>

* [BufferedReader](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html) - Used to read from CSV files
* [FileReader](https://docs.oracle.com/javase/8/docs/api/java/io/FileReader.html) - Used to read from CSV files
* [IOException](https://docs.oracle.com/javase/7/docs/api/java/io/IOException.html) - Used to except missing files
* [ArrayList](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html) - Used in StockAsTable class
* [Arrays](https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html) - Used in CSV Reader 
* [HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) - Used in customer transactions
* [Scanner](https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html) - Used to accept user input 
* [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) - Used in StockAsTable
* [Hibernate](https://docs.jboss.org/hibernate/core/3.5/api/org/hibernate/SessionFactory.html) - Used to connect to the database
* [InputMismatchException](https://docs.oracle.com/javase/7/docs/api/java/util/InputMismatchException.html) - Used to handle incorrect data types passed
* [BufferedWriter](https://docs.oracle.com/javase/7/docs/api/java/io/BufferedWriter.html) - Used to write to a CSV file
* [FileWriter](https://docs.oracle.com/javase/7/docs/api/java/io/FileWriter.html) - Used to write to a CSV file
* [Javax.persistence](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html) - Used to create queries

<h3> Authors </h3>

* Name: <b> Douglas Grey </b> Student Number: <b> 190357814 </b> 
* Name: <b> Josh Davison </b> Student Number: <b> 190107231 </b> 
* Name: <b> Lee Taylor </b> Student Number: <b> 190211479 </b> 
* Name: <b> Sofia Trevino </b> Student Number: <b> 190209546 </b> 
* Name: <b> Tiger Kato </b> 

<h3> Acknowledgements </h3>

* [CRUD Interface](https://nucode.ncl.ac.uk/scomp/stage1/csc1035/practicals/part-2/csc1035-hibernate-examples/tree/master/src/main/java/controller) - Used as a base code for CRUD operations - <i> Jordan Barnes </i>
* [Reading From a CSV File In Java](https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/) - Used to read from 
the CSV file - <i> Mkyoung </i>

<h3> Note </h3>

Whenever a file is outputted i.e CSV or .txt, the file is, created and written to, this can be confirmed by using  
file explorer to check the output contents. However there is a limitation in the IDE - if the file is outputted within  
this directory, the IDE's file explorer does not show the newly created file unless the program's execution is halted.  
This is caused by the IDE and not the methods used to create and write to the file.