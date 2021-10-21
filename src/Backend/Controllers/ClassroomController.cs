using Microsoft.AspNetCore.Mvc;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class ClassroomController : ControllerBase
{
    [HttpGet]
    public ActionResult<IEnumerable<int>> GetClassrooms()
    {
        return new[] { 101, 102, 103, 202, 203, 204 };
    }
}