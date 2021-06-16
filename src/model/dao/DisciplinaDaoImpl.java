package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.db.DB;
import model.entities.Aluno;
import model.entities.Disciplina;

public  class DisciplinaDaoImpl implements DisciplinaDAO {

	private Connection conexao;
	
	public DisciplinaDaoImpl(Connection conexao) {
		this.conexao = conexao;
	}
	
	@Override
	public void insert(Disciplina obj) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conexao.prepareStatement(
					"INSERT INTO disciplina (iddisciplina, nomedisciplina, cargahoraria) VALUES (?, ?, ?) ", 
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setInt(1, obj.getIddisciplina());
			pst.setString(2, obj.getNomedisciplina());
			pst.setInt(3, obj.getCargahoraria());

			int linhas = pst.executeUpdate();
				
			if (linhas > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
						obj.setIddisciplina(rs.getInt(1));
				}
				System.out.println("    Disciplina [ " 
									+ obj.getIddisciplina() + " | " 
									+ obj.getNomedisciplina() + "|"
									+ obj.getCargahoraria() + " ] "
									+ " foi criado com sucesso!");
			}
			else {
				System.out.println("    Não foi possível cadastrar a Disciplina!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaPreparedStatement(pst);
			DB.fechaResultSet(rs);
		}
	}
	
	@Override
	public void update(Disciplina obj) {
		
		PreparedStatement pst = null;
		try {
			pst = conexao.prepareStatement("UPDATE disciplina SET  nomedisciplina=?, cargahoraria=? "
										+ "	WHERE iddisciplina=?");
			pst.setString(1, obj.getNomedisciplina());
			pst.setInt(2, obj.getCargahoraria());
			pst.setInt(3, obj.getIddisciplina());
			
			int linhas = pst.executeUpdate();
			if (linhas > 0) {	
				System.out.println("Disciplina [ " 
								+ obj.getIddisciplina() + " | " 
								+ obj.getNomedisciplina() + "|"
								+ obj.getCargahoraria() + " ] "
								+ " alterada com sucesso!");
			}
			else {
				System.out.println("    Não foi realizada a alteração da Disciplina!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaPreparedStatement(pst);
		}
	}

	@Override
	public void deleteByid(Integer id) {
		
		PreparedStatement pst = null;
		try {
			pst = conexao.prepareStatement("DELETE FROM disciplina WHERE iddisciplina = ?");
			pst.setInt(1, id);
			
			int linhas = pst.executeUpdate();
			if (linhas > 0) {	
				System.out.println("    Disciplina [" + id + "] excluída com sucesso!");
			}
			else {
				System.out.println("    Não foi possível excluir a Disciplina id[" + id + "] !");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaPreparedStatement(pst);
		}
		
	}

	@Override
	public Disciplina findByid(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conexao.prepareStatement(
					"SELECT a.*, a.nomedisciplina FROM disciplina a WHERE iddisciplina = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				Disciplina a = new Disciplina();
				a.setIddisciplina(rs.getInt(0));
				a.setNomedisciplina(rs.getString(1));
				a.setCargahoraria(rs.getInt(2));
				
				return a;
			}
			return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaPreparedStatement(pst);
			DB.fechaResultSet(rs);
		}	
		return null;
	}

	@Override
	public List<Disciplina> findAll() {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Disciplina> lista = new ArrayList<Disciplina>();	
		
		try {
			pst = conexao.prepareStatement("SELECT * FROM disciplina");
			rs = pst.executeQuery();
			while (rs.next()) {   
				Disciplina d = new Disciplina(rs.getInt("iddisciplina"), rs.getString("nomedisciplina"), rs.getInt("cargahoraria"));
				lista.add(d);
			}
		}
		catch (SQLException e) {
			System.out.println(e);
		}
		finally {
			DB.fechaPreparedStatement(pst);
			DB.fechaResultSet(rs);
		}
			
		
		return lista;
	}
}
