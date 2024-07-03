using System;
using System.Collections.Generic;

namespace GradeBook
{
    public class Book
    {
        public Book(string name)
        {
            grades=new List<double>();
            Name=name;
        }
    
        public void AddGrade(double grade)
        {
            if (grade<=100 && grade >=0)
            {
                grades.Add(grade);
            }
            else
            {
                Console.WriteLine("Invalid value for the grade!");
            }
        }

        public Statistics GetStatistics()
        {
            var result=new Statistics();

            foreach (var grade in grades)
            {
                result.Low=Math.Min(grade, result.Low);
                result.High=Math.Max(grade, result.High);
                result.Average+=grade;
            }
            result.Average/=grades.Count;
            return result;
        }

        private List<double> grades;
        public string Name;
    }

}