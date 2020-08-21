using System;
using System.IO;
using System.Text.RegularExpressions;

namespace CountBookmarks
{
    public class LogHandle
    {
        private string LogName; //internal property of the class, not available from outside

        public LogHandle(string fname) //constructor, just to check if OPS logfile exists and populating LogName upon constructing the LogHandle object
        {
            if (!File.Exists(fname))
            {
                throw new ArgumentException($"OPS log does not exist", fname);
            }
            LogName = fname;
        }

        private bool CheckLog(string line) //checks if logfile is OPS log
        {
            return line.Contains("Severity,Type,Code,Document Url,File,Message,Source,Line,Time,");
        }

        public enum BookmarkType //to visualize the return types,
        //must be available from outside so we can use it to call CountBookmarks with proper error type
        {
            EXTERNAL_BOOKMARK = 1,
            INTERNAL_BOOKMARK = 2,
            NO_BOOKMARK = 3,
            TOO_MANY_BOOKMARKS = 4
        }

        public int CountBookmarks(BookmarkType type)
        //the main caslculation routine, works on the LogName property of the class, set when constructing the object with a log filename
        //need to provide WHAT type of errors it should check, and it returns the number of found problems
        //except the constant and the contructor, this is the only public item, it does all the important stuff
        {
            using (var reader = File.OpenText(LogName)) //take advantage of IODisposable object cleanup
            {
                var firstLine = reader.ReadLine();
                if (firstLine == null)
                {
                    throw new Exception("Unable to process file, file is empty");
                }
                if (!CheckLog(firstLine))
                {
                    throw new Exception("Unable to process file, not valid OPS log file");
                }
                var result = 0;
                string line = reader.ReadLine(); //trying to read next line in log which should hold valid log data now
                while (line != null)
                {
                    if (CheckBookmark(line) == type) //check the type of error
                    {
                        result++;
                    }
                    line = reader.ReadLine();
                }
                return result;
            }
        }
        private BookmarkType CheckBookmark(string logline)
        //rewritten to use our string type error constants
        {
           if (!logline.Contains(",InvalidBookmark,")) 
           //if log error is not an InvalidBookmark, it is not a bookmark issue, no need to check.
           {
               return BookmarkType.NO_BOOKMARK;
           }

            var regex = new Regex(@"(?<=\/)[^\/]+?\.md");
            var matches = regex.Matches(logline);
            if (matches.Count == 2)
            {
                if (matches[0].Value == matches[1].Value)
                {
                    return BookmarkType.INTERNAL_BOOKMARK;
                }
                else
                {
                    return BookmarkType.EXTERNAL_BOOKMARK;
                }
            }
            else
            {
                return BookmarkType.TOO_MANY_BOOKMARKS;
            }
        }

    }
}
