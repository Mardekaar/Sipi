# Load the old and new CSV datasets
$oldCsvPath = "c:\Users\tibors\OneDrive - RWS\TB_generator\OKX_TB-1010408.csv"
$newCsvPath = "c:\Users\tibors\OneDrive - RWS\TB_generator\2024-08-21_1724169603_all.csv"

$oldData = Import-Csv -Path $oldCsvPath
$newData = Import-Csv -Path $newCsvPath

# Create a hash table for quick lookup of new entries
$newEntries = @{}
foreach ($entry in $newData) {
    if ($entry.PSObject.Properties['en_US'] -ne $null) {
        $newEntries[$entry.en_US] = $entry
    }
}

# Loop over old dataset and process according to the conditions
foreach ($oldRow in $oldData) {
    if ($oldRow.PSObject.Properties['en_us'] -ne $null) {
        $enUsValue = $oldRow.en_us
        if ($newEntries.ContainsKey($enUsValue)) {
            # Update existing entry
            $newRow = $newEntries[$enUsValue]

            # Update fields based on rules
            $oldRow.concept_definition = $newRow.description

            # Update all usage fields from the new description
            foreach ($property in $oldRow.PSObject.Properties) {
                if ($property.Name -like "usage*") {
                    $oldRow."$($property.Name)" = $newRow.description
                }
            }

            # Check translatable field and modify concept_note if needed
            if ($newRow.translatable -eq "no" -or $newRow.translatable -eq "NO" -or $newRow.translatable -eq "FALSE") {
                $oldRow.concept_note += " DNT term"
            }

            # Update forbidden fields based on rules
            foreach ($property in $oldRow.PSObject.Properties) {
                if ($property.Name -like "forbidden*") {
                    if ($oldRow.forbidden -eq "no" -or $oldRow.forbidden -eq "NO" -or $oldRow.forbidden -eq "FALSE") {
                        $oldRow."$($property.Name)" = "FALSE"
                    }
                }
            }

            # Update case fields based on conditions
            foreach ($property in $oldRow.PSObject.Properties) {
                if ($property.Name -like "case*") {
                    if ($oldRow.casesensitive -eq "no" -or $oldRow.casesensitive -eq "NO" -or $oldRow.casesensitive -eq "FALSE") {
                        $oldRow."$($property.Name)" = "FALSE"
                    }
                    elseif ($oldRow.casesensitive -eq "yes" -or $oldRow.casesensitive -eq "YES" -or $oldRow.casesensitive -eq "TRUE") {
                        $oldRow."$($property.Name)" = "TRUE"
                    }
                }
            }

            # Update POS fields from tags
            foreach ($property in $oldRow.PSObject.Properties) {
                if ($property.Name -like "POS*") {
                    $oldRow."$($property.Name)" = $newRow.tags
                }
            }

            # Update ll-CC language fields
            foreach ($languageProperty in $newRow.PSObject.Properties) {
                if ($languageProperty.Name -match '^[a-z]{2}_[A-Z]{2}$') {
                    # Match language codes (ll-CC)
                    $oldRow."$($languageProperty.Name)" = $newRow."$($languageProperty.Name)"
                }
            }
        }
        else {
            # If not found in new CSV, mark old entry for deletion
            $oldRow.CID += "|delete"
        }
    }
    else {
        Write-Warning "Row in old data has no 'en_us' value: $oldRow"
    }
}

# Check for new entries and add them to the old dataset
foreach ($newRow in $newData) {
    if ($newRow.PSObject.Properties['en_US'] -ne $null) {
        if (-not ($oldData.CID -contains $newRow.en_US)) {
            $newEntry = New-Object PSObject -Property @{
                CID                = $newRow.en_US
                concept_definition = $newRow.description
                concept_note       = if ($newRow.translatable -eq "no" -or $newRow.translatable -eq "NO" -or $newRow.translatable -eq "FALSE") { "$($newRow.description) DNT term" } else { $newRow.description }

                # Initialize usage fields with new description
                usage1             = $newRow.description
                usage2             = $newRow.description
                # Continue for additional usage fields...

                # Add forbidden and case fields
                forbidden1         = $newRow.forbidden
                # Continue for additional forbidden fields...
                case1              = $newRow.casesensitive
                # Continue for additional case fields...

                # Add POS fields
                POS1               = $newRow.tags
                # Continue for additional POS fields...

                # Add language fields
                ru_RU              = $newRow.ru_RU
                # Continue for additional language fields...
            }
            $oldData += $newEntry
        }
    }
}

# Export the updated old dataset to a new CSV file
$oldCsvOutputPath = "C:\path\to\updated_old.csv"
$oldData | Export-Csv -Path $oldCsvOutputPath -NoTypeInformation
