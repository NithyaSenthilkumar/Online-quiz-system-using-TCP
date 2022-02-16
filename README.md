# Online-quiz-system-using-TCP
An interactive online quiz system portal using TCP  multithreading to cater unlimited clients at the same time which allows exam wing to conduct quiz efficiently. This is a TCP protocol based online quiz system using Java. 

        With the advancement of technology, the web is becoming important in our daily lives. In which we do virtual things based on the use of the web. 
        It is not only limited to the computers but it contains the different kinds of digital devices such as mobile. 
        Client-Server model is also a part of the web in which communication play an important role between the client and server . 
        Client-server divide a load of application development time by dividing the functionalities. 
        In this client is the requester and server are used as a service provider. 
        In the client-server system, the data processing is handled by the server and the response is provided to the client.

         There are two programs, Server and Client .
         Server waits for Clients to connect , once the connection is established after  successful login of the Client , the Server retrieves quiz questions from database using JDBC connectivity and stores them in HashMap data structure which is later sent to Client.
         After the successful submission of the quiz by Client , the score secured by the Client is sent back to Server which uploads the score to database using JDBC connectivity.
