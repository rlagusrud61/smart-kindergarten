using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddSwaggerGen(c => { c.SwaggerDoc("v1", new() { Title = "KindergartenApi", Version = "v1" }); });

var app = builder.Build();

// if (app.Environment.IsDevelopment())
// {
    app.UseSwagger();
    app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "KindergartenApi v1"));
// }

// app.UseRouting();

app.UseCors(m =>
{
    // m.WithOrigins("https://admin.mineapple.net", "http://localhost:3001", "https://localhost:3001", "https://192.168.1.2:3001",
    //     "http://192.168.1.2:3001");
    m.AllowAnyOrigin();
    m.AllowAnyMethod();
    m.AllowAnyHeader();
});

app.UseAuthorization();

app.MapControllers();

app.Run();