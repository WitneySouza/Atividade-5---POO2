package view;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.dao.DisciplinaDAO;
import model.dao.FactoryDAO;
import model.entities.Aluno;
import model.entities.Disciplina;

public class TelaDisciplina {
	
	static DisciplinaDAO disciplinaDAO = FactoryDAO.createDisciplinaDAO();
	
	@SuppressWarnings("resource")
	public static Scanner MenuDisciplina(Scanner console) throws InterruptedException, ParseException {
		int opcao = 0;
		do {
			System.out.println("\n\n");
			System.out.println("    ###   Tela: Disciplina     ###");
			System.out.println("    =========================");
			System.out.println("    |     1 - Cadastrar     |");
			System.out.println("    |     2 - Listar        |");
			System.out.println("    |     3 - Alterar       |");
			System.out.println("    |     4 - Excluir       |");
			System.out.println("    |     0 - Retornar      |");
			System.out.println("    =========================");
			System.out.print("    Op��o -> ");
			opcao = console.nextInt();
			console.nextLine();
			
			switch (opcao) {
			case 1: console = cadastrar(console);
					break;
			case 2:	console = listar(console);
					break;
			case 3: console = alterar(console);
					break;
			case 4: console = excluir(console);
					break;
			case 0:	console = TelaPrincipal.menuPrincipal(console);
					break;
			default:
				System.out.println("Op��o inv�lida!");
				TimeUnit.SECONDS.sleep(1);
			}
		} while (opcao != 0);
		return console;
	}

	private static Scanner excluir(Scanner console) throws InterruptedException {
		System.out.println("\n\n");
		System.out.println("    ###   Disciplina-Excluir   ###");
		System.out.println("    =========================");
		System.out.print("    |     Digite o Id: ");
		int id = console.nextInt();
		console.nextLine();
		System.out.println("    =========================");
		
		disciplinaDAO.deleteByid(id);
		
		console.nextLine();
		return console;
	}

	private static Scanner alterar(Scanner console) {
		Disciplina d = new Disciplina(); 
		
		System.out.println("\n\n");
		System.out.println("    ###   Disciplina-Alterar   ###");
		System.out.println("    =========================");  		
		System.out.print("    |     Id: "); 
		d.setIddisciplina(console.nextInt()); 
		//console.nextInt();
		  
		System.out.print("    |     Disciplina: "); 
		d.setNomedisciplina(console.next());
		//console.next();
		  
		System.out.print("    |     Carga Hor�ria: ");
		d.setCargahoraria(console.nextInt());
		//console.nextInt();
				  
		System.out.println("    =========================");
		
		disciplinaDAO.update(d);
		
		console.nextLine();
		return console;
	}

	private static Scanner listar(Scanner console) {
		
		List<Disciplina> disciplinas = disciplinaDAO.findAll();
		
		System.out.println("\n\n");
		System.out.println("    ###   Disciplina-Listar    ###");
		System.out.println("    =========================");
		System.out.println("    |     Iddisciplina\t\tnomedisciplina\t\tCargaHoraria");
		for(Disciplina a : disciplinas) { 
			System.out.println("| Disciplina:"  + a.getIddisciplina()
								+ "\t\t\t"	    + a.getNomedisciplina()
								+ "\t\t\t" 		+ a.getCargahoraria()); 
		}
		System.out.println("    =========================");
		console.nextLine();
		return console;
	}

	private static Scanner cadastrar(Scanner console) {
		Disciplina a = new Disciplina(); 
		
		System.out.println("\n\n");
		System.out.println("    ###   Disciplina-Cadastrar ###");
		System.out.println("    =========================");
		System.out.print("    |   Id  Disciplina: "); 
	    a.setIddisciplina(console.nextInt());
	    
		System.out.print("    |     Nome Disciplina: "); 
	    a.setNomedisciplina(console.next());
	    
		  
	    System.out.print("    |     Carga Hor�ria: "); 
	    a.setCargahoraria( console.nextInt());
	    System.out.println("    =========================");
	    
	    disciplinaDAO.insert(a);
	    
	    console.nextLine();
	    return console;
	}
}
