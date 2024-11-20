@echo off
:: Define a porta de execução
set DEFAULT_PORT=8080

echo Iniciando DebtorTracker na porta %DEFAULT_PORT%...
powershell -Command "java -jar (Get-ChildItem -Path . -Filter 'DebtorTracker*.jar' | Sort-Object LastWriteTime -Descending | Select-Object -First 1).FullName --server.port=%DEFAULT_PORT%"
pause