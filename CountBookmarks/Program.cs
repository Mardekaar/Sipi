using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

namespace CountBookmarks
{
    class Program
    {
        static void Main(string[] args)
        {
            //check if we have at least one command line argument given
            if (args.Length == 0)
            {
                System.Console.WriteLine("Please provide the full name and path of each OPS log files to check!");
                return;
            }
            //going through each provided OPS log
            foreach (var fname in args)
            {
                ProcessLogFile(fname); //minimizing the main function by moving repeating task to external function
            }
        }
        
        private static void ProcessLogFile(string fname) //since it is in the main Program class, it must be static
        {
            //the new minimal main program just opens the needed files (logs and corresponding results),
            //and writes the results to result file which is calculated by the LogHandle class
            //exceptions are thrown in file handling routines, but we catch all of them, just to make sure nothing escapes.
            //only file handling exceptions could happen in the code, all indicates a problem where we cannot process that logfile,
            //so we just need to skip processing it
            try
            {
                var file = new LogHandle(fname);
                var outputFilePath = Path.GetDirectoryName(fname) + "\\BookmarkIssues_" + Path.GetFileName(fname);
                using (var outputfile = File.CreateText(outputFilePath)) //take advantage of IODisposable object cleanup
                {
                    outputfile.WriteLine($"Number of external bookmark problems: {file.CountBookmarks(LogHandle.BookmarkType.EXTERNAL_BOOKMARK)}");
                    //EXTERRNAL_BOOKMARK is a constant, defined in our LogHandle class. we search only this type of error, but the code can find others as well
                    //we write the number of found problems into a result file, stored next to the logfile
                }
            }
            catch (Exception ex)
            {
                Console.Error.WriteLine(ex);
            }
        }
    }
}
