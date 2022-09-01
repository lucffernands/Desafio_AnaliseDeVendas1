package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sales;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path =  sc.next();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sales> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sales(
						Integer.parseInt(fields[0]), 
						Integer.parseInt(fields[1]), 
						fields[2], 
						Integer.parseInt(fields[3]), 
						Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
								
			List<Sales> vendas = list.stream()
					.filter(x -> x.getYear() == 2016)
					.sorted((d1, d2) -> d2.averagePrice().compareTo(d1.averagePrice()))
					.limit(5)
					.collect(Collectors.toList());
						
			vendas.forEach(System.out::println);
			
			Double sum = list.stream()
					.filter(x -> x.getSeller().equals("Logan"))
					.filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			System.out.println();
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " 
			+ String.format("%.2f", sum));
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		sc.close();
	}
}
