cd /d %~dp0
for /r proto %%i in (*.proto) do protoc-3.3.0.exe  --proto_path=%~dp0proto\ --java_out=server %%i
for /r proto %%i in (*.proto) do protoc-3.3.0.exe  --proto_path=%~dp0proto\ --descriptor_set_out=client\%%~ni.pb --include_imports %%i

pause