# Ejecutar como: powershell -ExecutionPolicy Bypass -File COMPILAR-TODO.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  COMPILANDO TODOS LOS MICROSERVICIOS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$base = $PSScriptRoot

$proyectos = @(
    @{ path = "eureka-server";         nombre = "Eureka Server" },
    @{ path = "peliculas\peliculas";   nombre = "Peliculas"     },
    @{ path = "clientes\clientes";     nombre = "Clientes"      },
    @{ path = "login\login";           nombre = "Login"         },
    @{ path = "rental\rental";         nombre = "Rental"        },
    @{ path = "pago\pago";             nombre = "Pago"          },
    @{ path = "inventario";            nombre = "Inventario"    },
    @{ path = "alertas";               nombre = "Alertas"       },
    @{ path = "reportes";              nombre = "Reportes"      },
    @{ path = "resenas";               nombre = "Resenas"       },
    @{ path = "gateway";               nombre = "Gateway"       }
)

$ok = 0
$fallo = 0

foreach ($p in $proyectos) {
    Write-Host "`nCompilando $($p.nombre)..." -ForegroundColor Yellow
    Set-Location "$base\$($p.path)"
    mvn clean package -DskipTests -q 2>&1 | Out-Null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  OK  $($p.nombre)" -ForegroundColor Green
        $ok++
    } else {
        Write-Host "  FALLO  $($p.nombre)" -ForegroundColor Red
        $fallo++
    }
}

Set-Location $base

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  $ok/11 compilados correctamente" -ForegroundColor $(if ($fallo -eq 0) { "Green" } else { "Red" })
if ($fallo -gt 0) {
    Write-Host "  $fallo servicios fallaron - revisa que Maven este instalado" -ForegroundColor Red
}
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Ahora ejecuta: powershell -ExecutionPolicy Bypass -File INICIAR-TODO.ps1" -ForegroundColor White
Write-Host ""
Write-Host "Presiona cualquier tecla para salir..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
