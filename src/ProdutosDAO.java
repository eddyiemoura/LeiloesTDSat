/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement st;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/leiloes?autoReconnect=true&useSSL=false", "root", "gojira2023");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }
    
    public int cadastrarProduto (ProdutosDTO produto){
        
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO produtos VALUES(?,?,?,?)");
            st.setInt(1, produto.getId());
            st.setString(2, produto.getNome());
            st.setInt(3, produto.getValor());
            st.setString(4, "não vendido");
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }
   
    
   public int venderProduto (ProdutosDTO produto){
       int status;
               try{
                  st = conn.prepareStatement("UPDATE produtos SET status = ? WHERE id = ?");
                  st.setString(1, "vendido");
                  st.setInt(2, produto.getId());
                  status = st.executeUpdate();
                  return status;
               }catch (SQLException ex){
                   System.out.println("Erro ao conectar: " + ex.getMessage());
                   return ex.getErrorCode();
               }
   }
   
    
    public List<ProdutosDTO> listar (){
        Connection conn;
        PreparedStatement stat;
        ResultSet rs;
        
        List<ProdutosDTO> produto = new ArrayList();
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/leiloes?autoReconnect=true&useSSL=false", "root", "gojira2023");
            stat = conn.prepareStatement("SELECT * FROM produtos");
            rs = stat.executeQuery();
            
            while (rs.next()){
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                produto.add(produtos);
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produto;
        
    }
    
    public List<ProdutosDTO> listarProdutosVendidos (){
        Connection conn;
        PreparedStatement stat;
        ResultSet rs;
        
        List<ProdutosDTO> produto = new ArrayList();
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/leiloes?autoReconnect=true&useSSL=false", "root", "gojira2023");
            stat = conn.prepareStatement("SELECT * FROM produtos WHERE status = ?");
            stat.setString(1, "vendido");
            rs = stat.executeQuery();
            
            while (rs.next()){
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                produto.add(produtos);
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return produto;
        
    }
    
   
    
    
    
    
   
    
    
    
   
    
    
        
}

