using System;
using System.Collections.Generic;
using System.IO;

namespace GradeBook
{
    class Program
    {
        static void Main(string[] args)
        {
            var book=new Book("Sipi's gradebook");
            string filePath;
            FileStream fileReader, fileWriter;


            book.AddGrade(12.1);
            book.AddGrade(13.1);
            book.AddGrade(14.1);
            book.AddGrade(15.1);
            var stats=book.GetStatistics();
            System.Console.WriteLine($"The average grade is {stats.Average}. Minimum is {stats.Low}, maximum is {stats.High}.");
            //return;

            if (args.Length==0)
            {
                System.Console.WriteLine("Please provide the full name and path of OPS log file to check!");
                return;
            }
            else
            {
                filePath=args[0];
                if (!File.Exists(filePath))
                {
                    System.Console.WriteLine("Please provide a valid/existing OPS log file!");
                    return;
                }
                else
                {
                    fileReader = new FileStream(filePath, FileMode.Open, FileAccess.Read, FileShare.Read);
                    fileWriter = new FileStream(".\result.txt", FileMode.Create, FileAccess.Write, FileShare.Write);

                    if (fileReader!=null)
                    {
                        fileReader.Close();
                    }
                    if (fileReader!=null)
                    {
                        fileReader.Close();
                    }
                }
            }

        }
    }
}
