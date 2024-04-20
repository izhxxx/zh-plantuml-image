def encode_plantuml(plantuml_text):
    # Encode text in UTF-8
    utf8_encoded = plantuml_text.encode('utf-8')
    # Compress using Deflate algorithm
    compressed = zlib.compress(utf8_encoded)
    # Reencode in ASCII using a transformation close to base64
    reencoded = base64.b64encode(compressed)
    # Convert reencoded to a string if it's not already
    # Convert reencoded to a string if it's not already
    if isinstance(reencoded, dict):
        reencoded_str = ''.join(reencoded.values())
    else:
        reencoded_str = reencoded

    # Replace base64 characters according to PlantUML mapping array
    encoded_plantuml = reencoded_str.translate(bytes.maketrans(
        b"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
        b"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"
    ))

    return encoded_plantuml.decode('utf-8')

# Example usage:
plantuml_text = '''
@startuml
Alice -> Bob: Authentication Request
Bob --> Alice: Authentication Response
@enduml
'''
encoded_plantuml = encode_plantuml(plantuml_text)
print(encoded_plantuml)
