#el escript debe recibir como parametro el nombre de la cadena.
auxrpcport=$(grep rpc-port ~/.multichain/$1/params.dat | cut -d'=' -f2)
auxuser=$(cat ~/.multichain/$1/multichain.conf | grep "rpcuser")
auxpass=$(cat ~/.multichain/$1/multichain.conf | grep "rpcpassword")

name="default.name=Default                   # name to display in the web interface"
rpchost="default.rpchost=127.0.0.1              # IP address of MultiChain node"
rpcport=$(echo "default.rpcport=")$auxrpcport
rpcuser=$(echo "default.")$auxuser$(echo "   #username for RPC from multichain.conf")
rpcpassword=$(echo "default.")$auxpass$(echo "   #password for RPC from multichain.conf")


sudo rm -r /opt/lampp/htdocs/demo/config.txt
sudo rm -r /opt/lampp/htdocs/api2/cli-multichain/config.txt

echo $name>>config.txt
echo $rpchost>>config.txt
echo $rpcport>>config.txt
echo $rpcuser>>config.txt
echo $rpcpassword>>config.txt


cp config.txt /opt/lampp/htdocs/demo/
cp config.txt /opt/lampp/htdocs/api2/cli-multichain/


rm -r config.txt