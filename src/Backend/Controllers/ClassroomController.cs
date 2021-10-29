using System.Linq;
using KindergartenApi.Context;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class ClassroomController : ControllerBase
{
    private readonly GartenContext _context;

    public ClassroomController(GartenContext context)
    {
        _context = context;
    }

    [HttpGet]
    public ActionResult<IAsyncEnumerable<string>> GetClassrooms()
    {
        return Ok(_context.Classrooms.Select(m => m.RoomIdentifier).AsAsyncEnumerable());
    }
}
