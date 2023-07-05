
import { BrowserRouter, Route, Routes } from "react-router-dom";
import styles from './style.module.css';
import HomeComponent from "./components/HomeComponent";
import MostrarProveedores from "./components/MostrarProveedores";
import IngresarProveedor from './components/IngresarProveedor';
import SubirAcopio from "./components/SubirAcopio";
import SubirPorcentajes from "./components/SubirPorcentajes";
import GenerarPlanilla from "./components/GenerarPlanilla";
import VerPlanilla from "./components/VerPlanilla";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes className={styles.container}>
          <Route path = "/" element = {<HomeComponent />}> </Route>
          <Route path = "/verProveedores" element  = {<MostrarProveedores />}> </Route>
          <Route path = "/ingresarProveedor" element  = {<IngresarProveedor />}> </Route>
          <Route path = "/subirAcopio" element = {<SubirAcopio />}> </Route> 
          <Route path = "/subirPorcentaje" element = {<SubirPorcentajes />}> </Route>
          <Route path = "/generarPlanilla" element = {<GenerarPlanilla />}> </Route>
          <Route path = "/verPlanilla" element = {<VerPlanilla />}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;