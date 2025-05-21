$objFSO = New-Object -ComObject Scripting.FileSystemObject
$objFolder = Get-Location

$specificExtension = ".xlf"  # Change this to the desired extension

# Read the language mappings from langmap.txt
$langMapFile = "$($objFolder.Path)\langmap.txt"
$langMap = @{}

if (Test-Path $langMapFile) {
    $langMapContent = Get-Content $langMapFile | ConvertFrom-Json
    $langMap = @{}
    foreach ($entry in $langMapContent.PSObject.Properties) {
        $langMap[$entry.Name] = $entry.Value
        Write-Output "Mapping: $($entry.Name) -> $($entry.Value)"
    }
}
else {
    Write-Output "langmap.txt not found in the current folder."
    exit
}

foreach ($objFile in Get-ChildItem -Path $objFolder.Path -File | Where-Object { $_.Extension -eq $specificExtension }) {
    $fileNameWithoutExtension = [System.IO.Path]::GetFileNameWithoutExtension($objFile.Name)
    $parts = $fileNameWithoutExtension.Split('_')
    $strLang = $parts[-1]  # Get the last part after the underscore

    if ($langMap.ContainsKey($strLang)) {
        $mappedLang = $langMap[$strLang]
    }
    else {
        $mappedLang = $strLang  # Use the original language code if no mapping is found
    }
    #Write-Output "Mapped lang: $mappedLang"
    $strFolderPath = "$($objFolder.Path)\$mappedLang\"

    if (-not $objFSO.FolderExists($strFolderPath)) {
        #$objFolder2 = $objFSO.CreateFolder($strFolderPath)
        $objFSO.CreateFolder($strFolderPath)
    }

    $objFSO.MoveFile($objFile.FullName, $strFolderPath)
}