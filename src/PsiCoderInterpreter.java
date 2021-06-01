import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class PsiCoderInterpreter {
    public static void main( String[] args ) throws IOException {
        Scanner sc = new Scanner( System.in );
        System.out.println( "Hola! Este es el interprete de PsiCoder creado por "
            + "Jonathan Lopez Castellanos y \nVictor Alfredo Barragan Paez para la materia "
            + "Lenguajes de Programacion." );
        System.out.println( "Dispones de 30 casos de prueba. Ingresando un numero entre "
            + "1 y 7 podras ver \nlos casos de prueba detallados de cada aspecto de PsiCoder. "
            + "Del caso 8 al \ncaso 30 son pruebas simples, muchas de ellas con errores hechos "
            + " a proposito." );
        System.out.println( "Podras agregar nuevos casos de prueba añadiendo archivos con el "
            + "nombre \n'in_' + un numero entre 31 y 40 + '.txt', a la carpeta 'input' del proyecto, "
            + "\nasi como ver los casos de prueba existentes" );
        int opt = 0;
        System.out.println( "Por favor. Ingresa un numero entre 1 y 30 si deseas ejecutar un "
            + "caso de \nprueba nuestro; de lo contrario, ingresa el numero correspondiente al \ncaso "
            + "de prueba que añadiste. Ingresa otro numero si deseas salir del interprete.\n" );
        opt = sc.nextInt();
        System.out.print( "\033[H\033[2J" );
        System.out.flush();
        if ( opt >= 1 && opt <= 40 ) {
            Scanner sc2 = new Scanner( new FileInputStream( new File( "input/in_" + opt + ".txt" ) ) );
            System.out.println( "\nCODIGO:\n" );
            while ( sc2.hasNextLine() )
                System.out.println( sc2.nextLine() );
            System.out.println( "\nRESULTADO:\n" );
            PsiCoderLexer lexer = new PsiCoderLexer( CharStreams.fromFileName( "input/in_" + opt + ".txt" ) );
            CommonTokenStream tokens = new CommonTokenStream( lexer );
            PsiCoderParser parser = new PsiCoderParser( tokens );
            ParseTree tree = parser.program();
            
            PsiCoderVisitorImpl loader = new PsiCoderVisitorImpl(
                new Scope( null, false ),
                new HashMap<String, Function>()
            );
            loader.visit( tree );
        }
    }
}

