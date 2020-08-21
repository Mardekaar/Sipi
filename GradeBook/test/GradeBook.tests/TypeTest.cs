using System;
using Xunit;

namespace GradeBook.Tests
{
    public class TypeTest
    {
        [Fact]
        public void Test1()
        {
            var book1 = GetBook("Book 1");
            GetBookSetName(book1, "Newww Name");
            Assert.Equal("New Name", book1.Name);
        }

        private void GetBookSetName(Book book, string name)
        {   
            //book = new Book(name);
            book.Name=name;
        }

        Book GetBook(string name)
        {
            return new Book(name);
        }
    }
}
