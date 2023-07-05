import React, {Component} from 'react';
import styles from '../style.module.css';
class HomeComponent extends Component {
    
    render() {

        
        const logo = "https://i.ibb.co/WzzwJzW/Logo-Lechera.png";
        return (
            
            <div >
                <div style={{ display: "flex", justifyContent: "center"}}>
                    <table> 
                        <tbody>
                            <tr>
                                
                                <h2 style={{display: "flex", justifyContent: "center"}}>¡Bienvenidos!</h2>
                                <a><img src={logo} alt="Logo-Lechera" border="0" /></a>
                                
                            </tr>
                        </tbody>
                    </table>
                </div>      
                <div>
                    <h1 style={{display: "flex", justifyContent: "center"}}>MilkStgo 2023</h1>
                </div>
                
                <nav>
                    <div>
                        <button className = {styles.navbutton}>
                            <a href="/verProveedores">Ver Proveedores</a>
                        </button>
                        <button className = {styles.navbutton}>
                            <a href="/subirAcopio">Importar datos de acopio</a>
                        </button>
                        <button className = {styles.navbutton}>
                            <a href="/subirPorcentaje">Importar datos de porcentajes de grasa y solido</a>
                        </button>
                        <button  className = {styles.navbutton}>
                            <a href="/generarPlanilla">Generar planilla de pago</a>
                        </button>
                    </div>

                </nav>

                
                <footer>
                    <p>Derechos reservados MilkStgo</p>
                </footer>
            </div>
            
        );
    }
}

export default HomeComponent;