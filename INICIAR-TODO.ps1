# Script de arranque completo - Proyecto Arriendo de Peliculas
# Ejecutar como: powershell -ExecutionPolicy Bypass -File INICIAR-TODO.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  SISTEMA DE ARRIENDO DE PELICULAS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# 1. Iniciar MySQL
Write-Host "`n[1/12] Iniciando MySQL..." -ForegroundColor Yellow
$mysqlPath = "C:\laragon\bin\mysql\mysql-8.4.3-winx64\bin\mysqld.exe"
$mysqlRunning = netstat -ano | Select-String ":3306"
if (-not $mysqlRunning) {
    Start-Process $mysqlPath -WindowStyle Hidden
    Start-Sleep -Seconds 4
}
Write-Host "       MySQL OK" -ForegroundColor Green

# 2. Iniciar Eureka
Write-Host "[2/12] Iniciando Eureka Server (puerto 8761)..." -ForegroundColor Yellow
Start-Process "java" -ArgumentList "-jar", "$PSScriptRoot\eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar" -WindowStyle Minimized
Start-Sleep -Seconds 18

# 3-11. Iniciar los 9 microservicios
$servicios = @(
    @{ jar = "$PSScriptRoot\peliculas\peliculas\target\peliculas-0.0.1-SNAPSHOT.jar"; nombre = "Peliculas   "; puerto = 8081; num = 3 },
    @{ jar = "$PSScriptRoot\clientes\clientes\target\clientes-0.0.1-SNAPSHOT.jar";         nombre = "Clientes    "; puerto = 8082; num = 4 },
    @{ jar = "$PSScriptRoot\login\login\target\login-0.0.1-SNAPSHOT.jar";                  nombre = "Login       "; puerto = 8083; num = 5 },
    @{ jar = "$PSScriptRoot\rental\rental\target\rental-0.0.1-SNAPSHOT.jar";               nombre = "Rental      "; puerto = 8084; num = 6 },
    @{ jar = "$PSScriptRoot\pago\pago\target\pago-0.0.1-SNAPSHOT.jar";                     nombre = "Pago        "; puerto = 8085; num = 7 },
    @{ jar = "$PSScriptRoot\inventario\target\inventario-0.0.1-SNAPSHOT.jar";              nombre = "Inventario  "; puerto = 8086; num = 8 },
    @{ jar = "$PSScriptRoot\alertas\target\alertas-0.0.1-SNAPSHOT.jar";                    nombre = "Alertas     "; puerto = 8087; num = 9 },
    @{ jar = "$PSScriptRoot\reportes\target\reportes-0.0.1-SNAPSHOT.jar";                  nombre = "Reportes    "; puerto = 8088; num = 10 },
    @{ jar = "$PSScriptRoot\resenas\target\resenas-0.0.1-SNAPSHOT.jar";                    nombre = "Resenas     "; puerto = 8089; num = 11 }
)

foreach ($s in $servicios) {
    Write-Host "[$($s.num)/12] Iniciando $($s.nombre)(puerto $($s.puerto))..." -ForegroundColor Yellow
    $cmd = "java -jar `"$($s.jar)`" --spring.profiles.active=dev"
    Start-Process "cmd.exe" -ArgumentList "/c", $cmd -WindowStyle Minimized
    Start-Sleep -Seconds 2
}

# 12. Iniciar Gateway
Write-Host "[12/12] Iniciando Gateway (puerto 8080)..." -ForegroundColor Yellow
Start-Process "java" -ArgumentList "-jar", "$PSScriptRoot\gateway\target\gateway-0.0.1-SNAPSHOT.jar" -WindowStyle Minimized

Write-Host "`nEsperando que todos los servicios arranquen (40 segundos)..." -ForegroundColor Cyan
Start-Sleep -Seconds 40

# Verificacion final
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  VERIFICACION DE SERVICIOS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$puertos = @(8761, 8080, 8081, 8082, 8083, 8084, 8085, 8086, 8087, 8088, 8089)
$nombres  = @("Eureka", "Gateway", "Peliculas", "Clientes", "Login", "Rental", "Pago", "Inventario", "Alertas", "Reportes", "Resenas")

$ok = 0
for ($i = 0; $i -lt $puertos.Length; $i++) {
    $conn = Test-NetConnection -ComputerName localhost -Port $puertos[$i] -WarningAction SilentlyContinue
    if ($conn.TcpTestSucceeded) {
        Write-Host "  OK  $($nombres[$i]) - puerto $($puertos[$i])" -ForegroundColor Green
        $ok++
    } else {
        Write-Host "  FAIL $($nombres[$i]) - puerto $($puertos[$i])" -ForegroundColor Red
    }
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  $ok/11 servicios activos" -ForegroundColor $(if ($ok -eq 11) { "Green" } else { "Red" })
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "  Eureka:  http://localhost:8761" -ForegroundColor White
Write-Host "  Gateway: http://localhost:8080/api/peliculas" -ForegroundColor White
Write-Host "  Swagger: http://localhost:8081/swagger-ui.html" -ForegroundColor White
Write-Host ""
Write-Host "Presiona cualquier tecla para salir..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
