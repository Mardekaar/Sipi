# CountExtBookmarkIssues

Console app to count how many external bookmark issues exist in the given OPS log files.

## Prerequisites:

[.Net Core 3.0 SDK or higher installed](https://dotnet.microsoft.com/download/dotnet-core/3.0)

## How to use:

You need to provide full path and filename on the command line for each OPS logs to check.
The script opens each log files one by one, checks if they exist and if they are valid OPS logs. Finally, it counts any possible external bookmark issues.
A new text file in the same folder as the original log will be created for each OPS logs, naming them by adding "BookmarkIssues_" to the beginning of the original filenames.
This result file will contain the number of external bookmark issues found.

## Possible extension:
The script is able to count external and internal bookmark issues, just need to add one extra line to main.
