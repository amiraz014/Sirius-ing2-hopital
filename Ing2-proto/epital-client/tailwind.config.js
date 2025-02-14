/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        'cursive': ['Brush Script MT', 'Brush Script Std', 'Lucida Calligraphy', 'Lucida Handwriting', 'Apple Chancery', 'cursive'],
      },
    },
  },
  plugins: [],
}

