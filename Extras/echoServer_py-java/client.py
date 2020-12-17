import socket

ip = "127.0.0.1"
portOut = 8888
# creazione socket UDP
sk = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) # SOCK_DGRAM specifica che è UDP

# per terminare server e client digitare exit come stringa di input

while(True):

    # invio il messaggio
    msg=input('Type a string: ')
    pac=None
    pac= bytes(msg, 'utf-8')
    sk.sendto(pac,(ip,portOut))

    # ricevo la risposta
    resp, address= sk.recvfrom(1024)
    print('Echo from server: %s' % resp.decode())
    if (msg== 'exit'):
        print('Closing request...')
        sk.close()
        break







