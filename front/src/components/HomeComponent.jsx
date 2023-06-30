import React, {Component} from 'react';

class HomeComponent extends Component {
    
    render() {
        return (
            <div>
            <header>
                <title>MilkStgo</title>
            </header>
            <div>
                <h1>MilkStgo 2023</h1>
            </div>
            <nav>
                <ul>
                    <li><a href="/listar-proveedores" class="cta-button"><i class="fas fa-users"></i> Consultar Proveedores</a></li>
                    <li><a href="/acopio" class="cta-button"><i class="fas fa-file-csv"></i> Subir Acopio</a></li>
                    <li><a href="/porcentajes" class="cta-button"><i class="fas fa-percent"></i>Subir Porcentajes</a></li>
                    <li><a href="/planilla" class="cta-button"><i class="fas fa-table"></i> Generar Planilla</a></li>
                </ul>
            </nav>
            <main>
                <div>
                    <table> 
                        <tbody>
                            <tr>
                                <th>
                                    <h2>¡Bienvenidos a MilkStago</h2>
                                    
                                </th>
                                <th>
                                    <a><img src="https://i.ibb.co/rQWMfbv/milkstgo.png" alt="milkstgo"/> </a>
                                </th>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
            <footer>
                <p>Derechos reservados MilkStgo</p>
            </footer>
        </div>
        );
    }
}

export default HomeComponent;