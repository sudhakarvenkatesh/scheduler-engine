# .SYNOPSIS
#     Generate proxy files, JobScheduler adapter dll and copy this and the jni4net dlls to the given locations
# .DESCRIPTION
#     the Script execute
#     - create a working temp folder in the user TEMP directory 
#     - generate two proxy files to the given locations and use "InputJobApiJar" basename as the proxy basename
#       1 - .jn4.dll
#       2 - .jn4.jar
#     - generate the JobScheduler adapter .dll (com.sos-berlin.jobscheduler.dotnet.adapter.dll)
#     - copied jni4net .dll files from the "proxygen" directory to the DLL location
#     - remove the working temp folder in the user TEMP directory
#
# .PARAMETER InputJobApiJar
#     Path to the .jar file with the job api (sos.spooler.*) implementation
# .PARAMETER DotnetJobSchedulerAdapterSourceDirectory
#     Path to the .cs files from the .NET JobScheduler adapter implementation
# .PARAMETER OutputDirectoryProxyDll
#     Output directory for the generated proxy .dll file
# .PARAMETER OutputDirectoryProxyJar
#     Boollean: Output directory for the generated proxy .jar file
#     By empty value - OutputDirectoryProxyDll will be used 
# .EXAMPLE
#     .\Generate-Jni4Net.ps1 "C:\Temp\com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.jar" "C:\Temp\adapter_cs" "C:\Temp\proxy"
#          1) copied the generated 
#               com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.j4n.dll
#               com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.j4n.jar
#          2) copied the existing jni4net dll files from the "proxygen" location 
#          to the "C:\Temp\proxy" location
#     .\Generate-Jni4Net.ps1 "C:\Temp\com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.jar" "C:\Temp\adapter_cs" "C:\Temp\proxy_dll" "C:\Temp\proxy_jar"
#          1) copied the generated 
#               com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.j4n.dll
#          2) copied the existing jni4net dll files from the "proxygen" location 
#          to the "C:\Temp\proxy_dll" location
#          3) copied the generated 
#               com.sos-berlin.jobscheduler.engine.engine-job-api-1.10.3.j4n.jar
#          to the "C:\Temp\proxy_jar" location
# ----------------------------------------------------------------------
# Command Line Arguments
# ----------------------------------------------------------------------
param(
	[parameter(Mandatory=$true)]
    [string] $InputJobApiJar,
	[parameter(Mandatory=$true)]
    [string] $DotnetJobSchedulerAdapterSourceDirectory,
    [parameter(Mandatory=$true)]
	[string] $OutputDirectoryProxyDll,
	[string] $OutputDirectoryProxyJar
)
# ----------------------------------------------------------------------
# Globals
# ----------------------------------------------------------------------
$Global:FrameworkDirectory      = "C:\Windows\Microsoft.NET\Framework64\v4.0.30319"
$Global:JDKDirectory            = "C:\Program Files\Java\jdk1.8.0_31"
# ----------------------------------------------------------------------
# Environment
# ----------------------------------------------------------------------
$env:APP_SCRIPT                 = $MyInvocation.MyCommand.Name
$env:APP_PATH                   = Split-Path $MyInvocation.MyCommand.Path
# ----------------------------------------------------------------------
# Script
# ----------------------------------------------------------------------
$Script:ProxyAssemblyBasename   = "com.sos-berlin.jobscheduler.dotnet.job-api.proxy" # This name is constant and can't be changed - the name defines the assembly name and is referenced by the another .dll files.
$Script:AdapterAssemblyBasename = "com.sos-berlin.jobscheduler.dotnet.adapter"       # 
$Script:ProxyGenDirectoryName   = "proxygen" 
$Script:Jni4NetDlls             = @("jni4net.n-0.8.8.0.dll","jni4net.n.w32.v40-0.8.8.0.dll", "jni4net.n.w64.v40-0.8.8.0.dll")
$Script:Jni4NetReferenceDll     = Join-Path $Script:ProxyGenDirectoryName "jni4net.n-0.8.8.0.dll"
$Script:JDKBinDirectory         = Join-Path $Global:JDKDirectory "bin"
$Script:TempDirectory           = $null
$Script:InputApiJar             = $null
$Script:InputApiJarVersion      = $null
$Script:TempApiJar              = $null
$Script:OutDirProxyDll          = $null
$Script:OutDirProxyJar          = $null
$Script:DotnetAdapterSourceDir  = $null
$Script:ProxyDll                = $null
$Script:ProxyJar                = $null
# ----------------------------------------------------------------------
# Implementation
# ----------------------------------------------------------------------
function Init([string] $outDirProxyDll,[string] $outDirProxyJar,[string] $dotnetAdapterSourceDirectory){
    $newPath = $Global:FrameworkDirectory+";"+$Script:JDKBinDirectory+";"+$env:Path;
    [Environment]::SetEnvironmentVariable("JAVA_HOME",$Global:JDKDirectory)
    [Environment]::SetEnvironmentVariable("PATH", $newPath)
    
    $Script:OutDirProxyDll = $outDirProxyDll
    $Script:OutDirProxyJar = $outDirProxyJar
    
    if([string]::IsNullOrEmpty($Script:OutDirProxyDll)){
        $Script:OutDirProxyDll = $env:APP_PATH
    }
    if([string]::IsNullOrEmpty($Script:OutDirProxyJar)){
        $Script:OutDirProxyJar = $Script:OutDirProxyDll
    }
    
    $Script:DotnetAdapterSourceDir = $dotnetAdapterSourceDirectory
}
function CreateTempDirectory(){
    $tempDirName            = "dotnet-proxy-"+[System.Guid]::NewGuid().ToString();
    $tempDirPath            = Join-Path $env:Temp $tempDirName
    $Script:TempDirectory   = New-Item -type directory -path $tempDirPath
}
function RemoveTempDirectory(){
    Set-Location $env:APP_PATH
    Remove-Item -path $Script:TempDirectory.Fullname -Recurse -Force
}
function CreateNewApiJarForProxy($jobApiJar){
    $Script:InputApiJar = Get-Item $jobApiJar
    
    SetApiJarVersion
    
    Set-Location $Script:TempDirectory
    
    [Array]$arguments   = "xvf",$jobApiJar
    ExecuteCommand "jar" $arguments;
    
    Remove-Item "com" -Recurse -Force
    Remove-Item "META-INF" -Recurse -Force
    Remove-Item "sos\spooler\jobs" -Recurse -Force
    Remove-Item "sos\spooler\Spooler_program.class" -Force
    
    $name = $Script:ProxyAssemblyBasename+".jar"
    [Array]$arguments = "cvf",$name,"sos\spooler"
    ExecuteCommand "jar" $arguments;
    
    Remove-Item "sos" -Recurse -Force
    
    $Script:TempApiJar  = Get-Item $name
}

function GenerateProxy(){
    Set-Location $env:APP_PATH
    
    $build              = Join-Path $Script:TempDirectory.Fullname "build"
    $command            = Join-Path $Script:ProxyGenDirectoryName "proxygen.exe" 
    [Array]$arguments   = """$Script:TempApiJar""","-wd","""$build"""
    ExecuteCommand $command $arguments;
    
    Set-Location $build
    $buildCommand       = Join-Path $build "build.cmd"
    ExecuteCommand $buildCommand $null;
        
    CopyGeneratedProxyFiles $build
}

function CopyGeneratedProxyFiles([string] $dir){
    Set-Location $env:APP_PATH
    
    $generatedTempDllName   = $Script:TempApiJar.Basename+".j4n.dll"
    $generatedTempJarName   = $Script:TempApiJar.Basename+".j4n.jar"
        
    $Script:ProxyDll    = Join-Path $dir $generatedTempDllName
    $Script:ProxyJar    = Join-Path $dir $generatedTempJarName
    
    
    $targetDllName      = RemoveInputApiJarVersion $Script:InputApiJar.Basename
    $targetDllName     += ".j4n"
    $targetDllName      = AddInputApiJarVersion $targetDllName "dll"
    
    $targetJarName      = RemoveInputApiJarVersion $Script:InputApiJar.Basename
    $targetJarName     += ".j4n"
    $targetJarName      = AddInputApiJarVersion $targetJarName "jar"
    
    $targetDll          = Join-Path $Script:OutDirProxyDll $targetDllName
    $targetJar          = Join-Path $Script:OutDirProxyJar $targetJarName
    
    if(Test-Path($targetDll)){
        Remove-Item $targetDll
    }
    if(Test-Path($targetJar)){
        Remove-Item $targetJar
    }
        
    Copy-Item $Script:ProxyDll $targetDll
    Copy-Item $Script:ProxyJar $targetJar
}

function SetApiJarVersion(){
    $arr = $Script:InputApiJar.Basename.Split("-")
    $vers = $arr[$arr.length-1];
    if ($vers.Replace(".","") -match "^[\d\.]+$"){
       $Script:InputApiJarVersion = $vers
    }
}

function GenerateJobSchedulerAdapterDll($proxyDll){
    $adapterName    = $Script:AdapterAssemblyBasename+".dll"
    $adapter        = Join-Path $Script:OutDirProxyDll $adapterName
    $powershellRef  = [PsObject].Assembly.Location
    
    if(Test-Path($adapter)){
        Remove-Item $adapter
    }
    
    $command            = "csc" 
    [Array]$arguments   = "/nologo","/warn:0","/t:library","/out:$adapter","/recurse:""$Script:DotnetAdapterSourceDir\*.cs""","/reference:$powershellRef;$Script:Jni4NetReferenceDll;""$Script:ProxyDll"""
    ExecuteCommand $command $arguments
    
    if(![string]::IsNullOrEmpty($Script:InputApiJarVersion)) {   
        $adapterName    = AddInputApiJarVersion $Script:AdapterAssemblyBasename "dll"
        $adapterNewPath = Join-Path $Script:OutDirProxyDll $adapterName
        Move-Item $adapter $adapterNewPath
    }    
}

function AddInputApiJarVersion([string] $basename,[string] $extension){
    if([string]::IsNullOrEmpty($Script:InputApiJarVersion)) {
        return $basename+"."+$extension
    }
    else{
        return $basename+"-"+$Script:InputApiJarVersion+"."+$extension
    }
}

function RemoveInputApiJarVersion([string] $basename){
    if([string]::IsNullOrEmpty($Script:InputApiJarVersion)) {
        return $basename
    }
    else{
        return $basename.Replace("-"+$Script:InputApiJarVersion,"")
    }
}

function CopyJni4NetDlls(){
    $Script:Jni4NetDlls |foreach {
        $oldDll = Join-Path $Script:OutDirProxyDll $_
        if(Test-Path($oldDll)){
            Remove-Item $oldDll;
        }
        $dll = Join-Path $Script:ProxyGenDirectoryName $_
        Copy-Item $dll $Script:OutDirProxyDll
    }
}

function ExecuteCommand([string] $command, [Array]$arguments){
    if($arguments -ne $null){
        $process = Start-Process $command -NoNewWindow -ArgumentList $arguments -Wait -PassThru 
    }
    else{
        $process = Start-Process $command -NoNewWindow -Wait -PassThru 
    }
	[Int32]$exitCode = $process.exitCode
	if($exitCode -eq 0)
	{
    }
	else
	{
	}
}

# ----------------------------------------------------------------------
# Main
# ----------------------------------------------------------------------
Init $OutputDirectoryProxyDll $OutputDirectoryProxyJar $DotnetJobSchedulerAdapterSourceDirectory
CreateTempDirectory
CreateNewApiJarForProxy $InputJobApiJar
GenerateProxy
GenerateJobSchedulerAdapterDll
CopyJni4NetDlls
RemoveTempDirectory 