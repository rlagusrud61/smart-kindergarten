﻿using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace KindergartenApi.Migrations
{
    public partial class StudentAge : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "Age",
                table: "Children",
                type: "integer",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Age",
                table: "Children");
        }
    }
}