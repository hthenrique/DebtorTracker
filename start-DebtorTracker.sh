#!/bin/bash

# Caminho para o JAR
JAR_PATH="DebtorTracker/"

JAR_PATH=$(ls -t $TARGET_DIR/DebtorTracker-*.jar | head -n 1)

# Porta do servidor (se quiser alterar a padrão)
SERVER_PORT=8080

# Verificar se o arquivo JAR existe
if [ ! -f "$JAR_PATH" ]; then
  echo "Arquivo JAR não encontrado em: $JAR_PATH"
  exit 1
fi

echo "Iniciando DebtorTracker na porta $SERVER_PORT..."
nohup java -jar "$JAR_PATH" --server.port=$SERVER_PORT > debtortracker.log 2>&1 &

# Obter o ID do processo iniciado
PID=$!
echo "DebtorTracker iniciado com PID $PID"
echo $PID > debtortracker.pid