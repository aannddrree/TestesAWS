import json
import csv

def json_to_csv(json_file, csv_file):
    # Ler o arquivo JSON
    with open(json_file, 'r') as f:
        data = json.load(f)
    
    # Abrir o arquivo CSV para escrita
    with open(csv_file, 'w', newline='') as f:
        # Se a estrutura do JSON for uma lista de dicionários
        if isinstance(data, list):
            # Obter as chaves do primeiro dicionário como cabeçalhos do CSV
            headers = data[0].keys()
            writer = csv.DictWriter(f, fieldnames=headers)
            writer.writeheader()
            writer.writerows(data)
        else:
            # Se a estrutura do JSON for um único dicionário
            writer = csv.DictWriter(f, fieldnames=data.keys())
            writer.writeheader()
            writer.writerow(data)

# Exemplo de uso
json_file = 'dados.json'
csv_file = 'dados.csv'
json_to_csv(json_file, csv_file)
