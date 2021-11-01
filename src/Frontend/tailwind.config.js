const colors = require('tailwindcss/colors')

module.exports = {
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  darkMode: 'class', // or 'media' or 'class'
  theme: {
    scale: {
      '25': '.25',
    },
    screens: {
      'sm': '640px',
      'md': '768px',
      'lg': '1024px',
      'xl': '1280px',
      '2xl': '1536px',
    },
    container:{
      padding: '2rem',
        center:true,
    },
    colors: {
      // inherit: colors.inherit,
      current: colors.current,
      transparent: colors.transparent,
      black: colors.black,
      white: colors.white,
      slate: colors.slate,
      gray: colors.gray,
      zinc: colors.zinc,
      neutral: colors.neutral,
      stone: colors.stone,
      red: colors.red,
      orange: colors.orange,
      amber: colors.amber,
      yellow: colors.yellow,
      lime: colors.lime,
      green: colors.green,
      emerald: colors.emerald,
      teal: colors.teal,
      cyan: colors.cyan,
      sky: colors.sky,
      blue: colors.blue,
      indigo: colors.indigo,
      violet: colors.violet,
      purple: colors.purple,
      fuchsia: colors.fuchsia,
      pink: colors.pink,
      rose: colors.rose,
    },
    fontFamily: {
      'sans': ['Nunito', 'sans-serif'],
      'serif': ['Nunito', 'serif'],
    },
    extend: {
      // backgroundColor: {
      //   primary: 'var(--color-bg-primary)',
      //   secondary: 'var(--color-bg-secondary)',
      // },
      // textColor: {
      //   accent: 'var(--color-text-accent)',
      //   primary: 'var(--color-text-primary)',
      //   secondary: 'var(--color-text-secondary)',
      // },
      spacing: {
        '128': '32rem',
        '144': '36rem',
      },
      borderRadius: {
        '4xl': '2rem',
      }
    }
  },
  variants: {
    extend: {
      borderColor: ['focus-visible'],
      opacity: ['disabled'],
    }
  },

  gridRow: {
    auto: 'auto',
    'span-1': 'span 1 / span 1',
    'span-2': 'span 2 / span 2',
    'span-3': 'span 3 / span 3',
    'span-4': 'span 4 / span 4',
    'span-5': 'span 5 / span 5',
    'span-6': 'span 6 / span 6',
    'span-full': '1 / -1',
  },
  gridRowStart: {
    auto: 'auto',
    1: '1',
    2: '2',
    3: '3',
    4: '4',
    5: '5',
    6: '6',
    7: '7',
  },
}
