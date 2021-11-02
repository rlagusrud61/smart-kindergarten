import {ThemeContext} from "./ThemeContext";
import {useContext} from "react";
import {WeatherMoon48Filled, WeatherSunny48Filled} from "@fluentui/react-icons"

export const Toggle = () => {
    const {theme,setTheme} = useContext(ThemeContext);

   return (
        <div className="transition duration-500 ease-in-out rounded-full p-2">
            {theme === "dark" ? (
                <WeatherSunny48Filled
                    onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
                    className="text-white dark:text-white font-bold text-sm cursor-pointer"
                />
            ) : (
                <WeatherMoon48Filled
                    onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
                    className="text-white dark:text-white font-bold text-sm cursor-pointer"
                />
            )}
        </div>
   );
}
