import sys
from pdf2docx import Converter

print(sys.argv[1])
print(sys.argv[2])

pdf_file = sys.argv[1]
docx_file = sys.argv[2]

# convert pdf to docx
cv = Converter(pdf_file)
cv.convert(docx_file)      # all pages by default
cv.close()
