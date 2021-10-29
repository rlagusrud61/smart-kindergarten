using KindergartenApi.Models.DTO;
using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.SwaggerGen;

namespace KindergartenApi;

public class SwaggerHubTypeFilter : IDocumentFilter
{
    public void Apply(OpenApiDocument swaggerDoc, DocumentFilterContext context)
    {
        context.SchemaGenerator.GenerateSchema(typeof(EventHistory), context.SchemaRepository);
    }
}