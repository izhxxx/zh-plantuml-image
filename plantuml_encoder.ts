import pako from 'pako'

function encodePlantUML(plantumlText: string): string {
  // Encode text in UTF-8
  const utf8Encoded: Uint8Array = new TextEncoder().encode(plantumlText)
  // Compress using Deflate algorithm
  const compressed: Uint8Array = pako.deflate(utf8Encoded)
  // Reencode in base64
  const reencoded: string = btoa(
    String.fromCharCode.apply(null, Array.from(compressed))
  )
  // Replace base64 characters according to PlantUML mapping array
  return reencoded.split('').map(translateBase64).join('')
}

function translateBase64(char: string): string {
  const base64Mapping =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
  const plantUmlMapping =
    '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_'

  const index = base64Mapping.indexOf(char)
  if (index !== -1) {
    return plantUmlMapping[index]
  }
  return char
}

function calcPlantUmlSvgUrl(plantumlText: string): string {
  const encode: string = encodePlantUML(plantumlText)
  return `https://www.plantuml.com/plantuml/svg/~1${encode}`
}

function replaceMarkdownPlantUmlBlocks(markdownText: string): string {
  const plantUmlRegex = /```plantuml\n([\s\S]*?)\n```/g
  return markdownText.replace(plantUmlRegex, (match, p1) => {
    const imageUrl = calcPlantUmlSvgUrl(p1.trim())
    return `![PlantUML Image](${imageUrl})`
  })
}

export { calcPlantUmlSvgUrl, replaceMarkdownPlantUmlBlocks }
