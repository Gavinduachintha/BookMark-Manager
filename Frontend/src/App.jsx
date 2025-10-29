import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import card from './card.jsx'
import Card from "./card.jsx";
function App() {
  const [count, setCount] = useState(0)

  return (
    <>
        <Card/>

    </>
  )
}

export default App
