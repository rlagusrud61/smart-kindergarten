import React, {createContext, useState, useEffect} from "react";

type ThemeName = "light" | "dark" | string
type ThemeContextType = {
    theme: ThemeName
    setTheme: (name: ThemeName) => void
}

// 이거 뭔지 알아내기
const getInitialTheme = () => {
    if (typeof window !== "undefined" && window.localStorage) {
        const storedPrefs = window.localStorage.getItem("color-theme");
        if (typeof storedPrefs === "string") {
            return storedPrefs;
        }
        const userMedia = window.matchMedia("(prefers-color-scheme: dark)");
        if (userMedia.matches) {
            return "dark";
        }
    }
    // If you want to use dark theme as the default, return 'dark' instead
    return "light";
};
interface ThemeContextProps{
    initialTheme : string,
    children : any;
}
export const ThemeContext = createContext<ThemeContextType>({} as ThemeContextType);

export const ThemeProvider = ({ initialTheme, children } : ThemeContextProps  ) => {
    const [theme, setTheme] = useState(getInitialTheme);


    const rawSetTheme = (theme: string) => {
        const root = window.document.documentElement;
        const isDark = theme === "dark";
        root.classList.remove(isDark ? "light" : "dark");
        root.classList.add(theme);
        localStorage.setItem("color-theme", theme);
    };
    if (initialTheme) {
        rawSetTheme(initialTheme);
    }
    useEffect(() => {
        rawSetTheme(theme);
    }, [theme]);
    return (
        <ThemeContext.Provider value={{ theme, setTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};