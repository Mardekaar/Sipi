namespace GradeBook
{
    public class Statistics
    {
        public double Average;
        public double High;
        public double Low;

        public Statistics()
        {
            Average=0.0;
            Low=double.MaxValue;
            High=double.MinValue;
        }
    }
}