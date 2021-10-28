using KindergartenApi.Context;
using KindergartenApi.Models;
using KindergartenApi.Models.DB;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class StudentsController : ControllerBase
{

    private readonly GartenContext _context;

    public StudentsController(GartenContext context)
    {
        _context = context;
    }

    [HttpGet]
    public ActionResult<IAsyncEnumerable<Student>> GetAllStudents()
    {
        return Ok(_context.Children.OrderBy(m => m.Name).AsAsyncEnumerable());
    }
}
