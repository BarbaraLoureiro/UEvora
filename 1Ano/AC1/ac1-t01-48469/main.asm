.globl main

.data
	menu_msg: 		.asciz "Escolha qual personagem quer ver:\n1-Yoda | 2-Darth Maul | 3-Mandalorian\n"
	opcao_yoda:		.string "1-Yoda"
	opcao_darthMaul:	.string "2-Darth Maul"
	opcao_mandalorian: 	.string "3-Mandalorian"
	
	image_rgb: 		.string "starwars.rgb" 
	image_final:		.string "output.rgb"
	buffer_rgb: 		.space 172800
	
.text
###################################################################################
# Função: read_rgb_image
# Descrição: lê um ficheiro com uma imagem no formato RGB para um array em memória.
#	a0 - string com a imagem rgb
#	a1 - endereço do buffer onde a imagem fica guardada
# Retorna:
#	a0 - buffer com imagem rgb
###################################################################################

read_rgb_image:
 
 	# Abrir o ficheiro de imagem em modo de leitura
 	li a7, 1024
	li a1, 0
	la a0, image_rgb
	ecall
	mv s6, a0
	
	# Ler os pixels da imagem
	li a7, 63
	mv a0, s6
	la a1, buffer_rgb	# endereço da imagem
	li a2, 172800
	ecall
	mv s7, a0
	
	# fecha o ficheiro
	li a7, 57
	mv a0, s6
	ecall
	
	mv a0, a1
	ret
	

###################################################################################
# Função: menu
# Descrição: calcula a componente Hue a partir das componentes R, G e B de um pixel.
# 	a0 - buffer com imagem rgb
# 	a1 - tamanho da imagem
# Retorna:
# 	a0 - personagem
###################################################################################

menu:
	# Exibir o menu de escolha da personagem
	la a0, menu_msg
	li a7, 4
	ecall
	
	# Ler a opção escolhida pelo usuário
	li a7, 5
	ecall
	
	li t0, 1
	beq a0, t0, opcao_valida	# yoda
	
	li t0, 2
	beq a0, t0, opcao_valida	# darth maul
	
	li t0, 3
	beq a0, t0, opcao_valida	# mandalorian
	
	li a0, 0
	ret
	
opcao_valida:
	ret

		
###################################################################################
# Função: location
# Descrição: esta função calcula a localização aproximada de um personagem na imagem. 
# A função calcula o centro de massa das localizações do personagem identificado. Retorna em a4 a posição cx e em a5 a posição cy.
#	a0 - personagem
#	a1 - buffer com imagem rgb
#	a2 - tamanho da imagem
# Retorna:
#	a4 - coordenada x do centro de massa do personagem (cx)
#	a5 - coordenada y do centro de massa do personagem (cy)
###################################################################################


location:
	# salva os registos na pilha
	addi sp, sp, -4 
	sw ra, 0(sp)

	li s8, 320	# comprimento
	li s7, 180	# altura
	
	li s4, 0 	# sumX
    	li s3, 0 	# sumY
    	li s2, 0 	# count
	
	mv s5, a1	# buffer
	mv a3, a0	# personagem
	
	li s10, 0 	# index y
	
loop_y:
    	li s11, 0 	# index x
    	
loop_x:
    	# calcula o index do pixel (y * comprimento + x) e guarda valor em s9
    	mul s9, s10, s8		# pixel index
    	add s9, s9, s11		# index + x
    	
    	# pixel = image[y * comprimento + x]		
    	# cada pixel tem 3 componentes (RGB)
    	li t6, 3
    	mul s9, s9, t6		# pixel index * 3
    	add s6, s5, s9 		# s6 aponta para o pixel-> buffer + pixel index = ponteiro
    	
    	# chama a indicator(pixel.r, pixel.g, pixel.b, personagem - a3)
    	lbu a0, 0(s6)  # retira o valor do componente R do pixel
    	lbu a1, 1(s6)  # retira o valor do componente G do pixel 
    	lbu a2, 2(s6)  # retira o valoe do componente B do pixel
    	jal indicator 
    	
    	# se a indicator retornar 0 (ñ pertence à personagem) salta para o x_increment, senão retorna 1 (pertence à personagem) e incrementa as somas
    	beqz a0, x_increment
	add s4, s4, s11		# sumX += x  soma dos pixeis x pertencentes à personagem
	add s3, s3, s10		# sumY += y  soma dos pixeis y pertencentes à personagem
	#addi s2, s2, 1		# count++ passou para a indicator
	
x_increment:
	addi s11, s11, 1	# x = x + 1
	blt s11, s8, loop_x 	# x < comprimento
	
	addi s10, s10, 1	# y = y + 1
	blt s10, s7, loop_y 	# y < altura
	
	# if (count > 0) (count -> nº de pixeis totais pertencentes)
	blez s2, else
	div t0, s4, s2		# sumX / count
	div t1, s3, s2		# sumY / count
	
	mv a4, t0		# cx = sumX / count
	mv a5, t1		# cy = sumY / count
	
	j end_location
	
else:
	li a0, -1		# return -1 se nenhum pixel da personagem for encontrado
	li a4, 0		# set cx e cy a 0
	li a5, 0
	j end_location
	

end_location:
	# restaura registo na pilha
	lw ra, 0(sp)
	addi sp, sp, 4 	
    	ret


###################################################################################
# Função: indicator
# Descrição: esta função recebe os componentes R, G e B do pixel e calcula o seu valor da hue que é retornado para a indicator
# 	a0 - r
# 	a1 - g
# 	a2 - b
# 	a3 - personagem
# retorna:
# 	a0 - 1 ou 0
###################################################################################


indicator:
	# salva os registos na pilha
	addi sp, sp, -4 
	sw ra, 0(sp)
	
	# chama a hue
	jal hue		# devolve a0 com valor da hue do personagem
	
	li t0, 1
	beq a3, t0, yoda		# personagem == 1 
	
	li t0, 2
	beq a3, t0, darth		# personagem == 2
	
	li t0, 3
	beq a3, t0, mandalorian		# personagem == 3
	
	j end				# else 

yoda:	
	li t0, 40
	li t1, 80
	blt a0, t0, end  # h < 40
	bgt a0, t1, end  # h > 80
	li a0, 1			# if (character == 1 && (h >= 40 && h <= 80))  return 1;
	addi s2, s2, 1		# count++
	j end2

darth:
	li t0, 1
	li t1, 15
	blt a0, t0, end  # h < 1
	bgt a0, t1, end  # h > 15
	li a0, 1			# else if (character == 2 && (h >= 1 && h <= 15)) return 1;
	addi s2, s2, 1		# count++
	j end2

mandalorian:
	li t0, 160
	li t1, 180
	blt a0, t0, end  # h < 160
	bgt a0, t1, end  # h > 180
	li a0, 1			# else if (character == 3 && (h >= 160 && h <= 180)) return 1;
	addi s2, s2, 1		# count++
	j end2

end:
	li a0, 0			# else return 0
	j end2
	
end2:  
	# restaura registo na pilha
	lw ra, 0(sp)
	addi sp, sp, 4
	ret
	

###################################################################################
# Função: hue
# Descrição: esta função recebe os componentes R, G e B do pixel e calcula o seu valor da hue que é retornado para a indicator
# 	a0 - r
# 	a1 - g
#	a2 - b
# 	a3 - personagem
# retorna:
# 	a0 - valor da hue
###################################################################################


hue:
	# salva os registos na pilha
	addi sp, sp, -4 
	sw ra, 0(sp)
	
	li t2, 60
	
#LARANJA
	bge a1, a0, sai			# G >= R 
	blt a1, a2, sai			# G < B
	sub t0, a1, a2			# G - B
	sub t1, a0, a2			# R - B
	mul t0, t0, t2			# 60*(g-b)
	div t0, t0, t1			# 60*(g-b)/(r-b)

	mv a0, t0
	j end_hue
	
sai:
#VERDEAMARELADO:	
	blt a1, a0, sai1		# G < R
	bge a2, a0, sai1		# B >= R
	sub t0, a0, a2			# R - B
	sub t1, a1, a2			# G - B
	mul t0, t0, t2			# 60*(r-b)
	div t0, t0, t1			# 60*(r-b)/(g-b)
	li t1, 120
	sub t0, t1, t0			# 120-60*(r-b)/(g-b)
	
	mv a0, t0
	j end_hue

sai1:
#VERDEPRIMAVERA:
	bge a2, a1, sai2		# B >= G
	blt a2, a0, sai2		# B < R
	sub t0, a2, a0 			# B - R
	sub t1, a1, a0			# G - R
	mul t0, t0, t2			# 60*(b-r)
	div t0, t0, t1			# 60*(b-r)/(g-r)
	li t1, 120			
	add t0, t1, t0			# 120+60*(b-r)/(g-r)
	
	mv a0, t0
	j end_hue

sai2:	
#AZURE:
	blt a2, a1, sai3		# B < G
	bge a0, a1, sai3		# R >= G
	sub t0, a1, a0			# G - R
	sub t1, a2, a0 			# B - R
	mul t0, t0, t2			# 60*(g-b)
	div t0, t0, t1			# 60*(g-b)/(r-b)
	li t1, 240	
	sub t0, t1, t0			# 240-60*(g-r)/(b-r)
	
	mv a0, t0
	j end_hue
	
sai3:
#VIOLETA:
	bge a0, a2, sai4		# R >= B
	blt a0, a1, sai4		# R < G
	sub t0, a0, a1			# R - G
	sub t1, a2, a1			# B - G
	mul t0, t0, t2			# 60*(r-g)
	div t0, t0, t1			# 60*(r-g)/(b-g)
	li t1, 240
	add t0, t1, t0			# 240+60*(r-g)/(b-g)
	
	mv a0, t0
	j end_hue

sai4:	
#ROSA:
	blt a0, a2, end_hue		# R < B	
	bge a1, a2, end_hue		# G >= B
	sub t0, a2, a1			# B - G
	sub t1, a0, a1			# R - G
	mul t0, t0, t2			# 60*(b-g)
	div t0, t0, t1			# 60*(b-g)/(r-g)
	li t1, 360
	sub t0, t1, t0			# 360-60*(b-g)/(r-g)
	
	mv a0, t0
	j end_hue
	
end_hue:
	# restaura registo na pilha
	lw ra, 0(sp)
	addi sp, sp, 4
	ret 
	
	
###################################################################################
# Função: write_rgb_image
# Descrição: esta função é responsável por escrever a imagem processada no arquivo da imagem final
# 	a0 - nome do ficheiro
# 	a1 - buffer da imagem
# 	a2 - tamanho 
# retorna:
#	a0 - ficheiro da imagem final
###################################################################################	
	
write_rgb_image:

	# abre o ficheiro em modo escrita
	li a7, 1024
	la a0, image_final
	li a1, 1
	ecall
	mv s6, a0
	
	# escreve os pixeis da imagem
	li a7, 64
	mv a0, s6
	la a1, buffer_rgb
	li a2, 172800
	ecall
	mv s7, a0
	
	# fecha o ficheirp
	li a7, 57
	mv a0, s6
	ecall
	
	ret
	
	
###################################################################################
# Função: draw_cross
# Descrição: desenha uma cruz no centro de massa da personagem
# 	a3 - coordenada x do centro de massa do personagem (cx)
#	a4 - coordenada y do centro de massa do personagem (cy)
# retorna;
#	a0 - imagem com a cruz desenhada
###################################################################################

draw_cross:
	# salva os registos na pilha
	addi sp, sp, -4 
	sw ra, 0(sp)

	la s5, buffer_rgb


	li t0, 3
	li t1, 960	# comprimento (320) * 3
	
	mul t2, t1, a5	# t2 = cy * 960
	mul t3, t0, a4	# t3 = cx * 3
	sub t4, t1, t3	# t4 = 960 - cx * 3
	sub t5, t2, t4 	# t2 - t4 
	add s5, s5, t5	# 172800 + t5 # aponta para o index do pixel do centro de massa
	
	li t0, 0
	li t1, 0
	li t2, 0
	
	sb t1, 0(s5)	# guarda a componente r do centro de massa
	sb t2, 1(s5)	# guarda a componente g do centro de massa
	sb t3, 2(s5)	# guarda a componente b do centro de massa
	
	li t0, 0	# count pixel
	li t1, 10	# dimensao da cruz
	li t2, 0	# vermelho
	li t3, 255	# vermelho
	li t4, 0	# vermelho
	li t5, 960	# comprimento * 3
	
direita:
	addi s5, s5, 3		# avança para o próximo pixel da direita
	sb t2, 0(s5) 		# desenha a parte vermelha da cruz
	sb t3, 1(s5)
	sb t4, 2(s5)
	addi t0, t0, 1		# count + 1  
	bne t0, t1, direita 	# count != 10 volta a desenhar até ao 10
	addi s5, s5, -30	# volta ao centro de massa
	addi t0, t0, -10 	# reinicia count
	
esquerda:
	addi s5, s5, -3		# avança para o próximo pixel da esquerda
	sb t2, 0(s5)		# desenha a parte vermelha da cruz
	sb t3, 1(s5)
	sb t4, 2(s5)
	addi t0, t0, 1		# count + 1  
	bne t0, t1, esquerda	# count != 10 volta a desenhar até ao 10
	addi s5, s5, 30		# volta ao centro de massa
	addi t0, t0, -10	# reinicia count
	
	li t2, 255	# verde
	li t3, 0	# verde 
	li t4, 0	# verde
	li t5, 9600	# 960 * dimensão da cruz
	
cima:
	addi s5, s5, -960	# anda para cima 960 
	sb t2, 0(s5)		# desenha a parte verde da cruz
	sb t3, 1(s5)
	sb t4, 2(s5)
	addi t0, t0, 1		# count++
	bne t0, t1, cima	# enquanto count != 960
	add s5, s5, t5		# volta para o centro de massa
	addi t0, t0, -10	# reinicia count
	
baixo:
	addi s5, s5, 960	# anda para baixo 960 
	sb t2, 0(s5)		# desenha a parte verde da cruz
	sb t3, 1(s5)
	sb t4, 2(s5)		
	addi t0, t0, 1		# count++
	bne t0, t1, baixo	# enquanto count != 960
	sub s5, s5, t5		# volta para o centro de massa
	addi t0, t0, -10	# reinicia count
	
	
	mv a0, s5
	
	# restaura registo na pilha
	lw ra, 0(sp)
	addi sp, sp, 4
	ret
	

###################################################################################
# Função: main
# Descrição: faz a chamada das funções
###################################################################################

main:
	jal read_rgb_image
	jal menu
	jal location
	jal draw_cross
	jal write_rgb_image
