namespace KindergartenApi.Models.DB;

public class Teacher
{
    public Teacher(string firstName, string lastName)
    {
        FirstName = firstName;
        LastName = lastName;
    }

    public Guid Id { get; set; }
    public string FirstName { get; set; }
    public string LastName { get; set; }

    public IEnumerable<Student> Students { get; set; }
}
