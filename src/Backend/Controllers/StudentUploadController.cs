using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.Mvc;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class StudentUploadController : ControllerBase
{
    [HttpPost]
    public async Task<IActionResult> UpdateNearbyBluetooth(string ownMac, IEnumerable<NearbyBluetooth> nearbySignals) // very secure yesyes
    {
        // TODO replace own mac with user account data

        return Ok();
    }
}
