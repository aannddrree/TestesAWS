def left_pad(s, size, pad_char=' '):
    if s is None:
        return None
    if size < 0:
        raise ValueError("O tamanho desejado não pode ser negativo")
    if len(pad_char) != 1:
        raise ValueError("O caractere de preenchimento deve ser um único caractere")
    
    padding_needed = size - len(s)
    if padding_needed <= 0:
        return s
    else:
        return pad_char * padding_needed + s

# Exemplos de uso
print(left_pad("abc", 5))           # Output: "  abc" (com espaços)
print(left_pad("abc", 5, '*'))      # Output: "**abc"
print(left_pad("abc", 2, '*'))      # Output: "abc" (não há necessidade de preenchimento)
print(left_pad("abc", 5, '0'))      # Output: "00abc"
print(left_pad("abc", 6, '-'))      # Output: "---abc"
print(left_pad("abcdef", 10, '#'))  # Output: "####abcdef"
