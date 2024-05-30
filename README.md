* IP4 vs IP6 *


* 2 Apps *
    1) Server
    2) Client

    1) TCP/IP Client Server Examples.
        - Reliable two way comm link between client and server.

* TYPICAL TCP/IP Client-Server Interaction *

    1) Server needs to first create a ServerSocket, and bind it to a port.
    2) Server calls accept() on the ServerSocket, which returns a Socket when a client connects.
    3) Client creates a Socket using a constructor that takes a host and port.
    4) Server and Client use their respective sockets to exchange data.