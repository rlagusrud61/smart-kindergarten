FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["KindergartenApi.csproj", "./"]
RUN dotnet restore "KindergartenApi.csproj"
COPY . .
WORKDIR "/src/"
RUN dotnet build "KindergartenApi.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "KindergartenApi.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "KindergartenApi.dll"]
