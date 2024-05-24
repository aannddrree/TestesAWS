import hashlib

def hash_sha256(input_string):
    # Criar o objeto hash SHA-256
    hash_object = hashlib.sha256(input_string.encode())
    # Obter o hash hexadecimal
    hex_dig = hash_object.hexdigest()
    return hex_dig

if __name__ == "__main__":
    input_string = input("Digite a string para hashear: ")
    hashed_string = hash_sha256(input_string)
    print("SHA-256 Hash:", hashed_string)
