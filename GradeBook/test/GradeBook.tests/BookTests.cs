using System;
using Xunit;

namespace GradeBook.Tests
{
    public class BookTests
    {
        [Fact]
        public void BookCalculatesAnAverageGrade()
        {
            //put test data and arrange onjects and values to be used
            var newbook=new Book("");
            newbook.AddGrade(10.5);
            newbook.AddGrade(20.5);
            newbook.AddGrade(30.0);

            //act section invoke methid, do something to produce a result
            var result = newbook.GetStatistics();
            
            //assert section, assert something on the value we received
            Assert.Equal(20.3, result.Average,1);
            Assert.Equal(30.0, result.High,1);
            Assert.Equal(10.6, result.Low, 1);
        }
    }
}
