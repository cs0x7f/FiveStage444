package fivestage444;

import java.io.PipedOutputStream;
import java.io.PipedInputStream;

import static fivestage444.Constants.*;

public final class Stage2Solver extends StageSolver{

	private static int stage2_twist_map1[] = {
	Uf, Uf3, Uf2, Df, Df3, Df2, Ufs, Ufs3, Ufs2, Dfs, Dfs3, Dfs2,
	Lf2, Rf2, Lfs2, Rfs2, Ff2, Bf2, Ffs2, Bfs2,
	Ffs, Ffs3, Bfs, Bfs3, Ffs, Ffs3, Bfs, Bfs3,
	Lfs, Lfs3, Rfs, Rfs3, Lfs, Lfs3, Rfs, Rfs3
	};

	private static int stage2_twist_map2[] = {
	-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	-1, -1, -1, -1, -1, -1, -1, -1,
	Ff3, Ff, Bf3, Bf, Ff, Ff3, Bf, Bf3,
	Lf3, Lf, Rf3, Rf, Lf, Lf3, Rf, Rf3
	};

	private static int stage2_block_map[] = {
	Uf, Uf3, Uf2, Us, Us3, Us2, Df, Df3, Df2, Ds, Ds3, Ds2,
	Ufs, Ufs3, Ufs2, Dfs, Dfs3, Dfs2, UsDs3, Us3Ds, Us2Ds2,
	Lf2, Ls, Ls3, Ls2, Rf2, Rs, Rs3, Rs2,
	Lfs2, Rfs2, LsRs3, Ls3Rs, Ls2Rs2,
	Ff2, Fs, Fs3, Fs2, Bf2, Bs, Bs3, Bs2,
	Ffs2, Bfs2, FsBs3, Fs3Bs, Fs2Bs2
	};

	private static int n_moves_metric_stg2[] = { N_STAGE2_SLICE_MOVES, N_STAGE2_TWIST_MOVES, N_STAGE2_BLOCK_MOVES};

	private static int SL_MS_X = 0;
	private static int SL_MS_U = 1;
	private static int SL_MS_u = 2;
	private static int SL_MS_d = 3;
	private static int SL_MS_D = 4;
	private static int SL_MS_L = 5;
	private static int SL_MS_l = 6;
	private static int SL_MS_r = 7;
	private static int SL_MS_R = 8;
	private static int SL_MS_F = 9;
	private static int SL_MS_f = 10;
	private static int SL_MS_b = 11;
	private static int SL_MS_B = 12;

	private static int STG2_TW_MS_X = 0;
	private static int STG2_TW_MS_u = 1;
	private static int STG2_TW_MS_U = 2;
	private static int STG2_TW_MS_d = 3;
	private static int STG2_TW_MS_D = 4;
	private static int STG2_TW_MS_l = 5;
	private static int STG2_TW_MS_L = 6;
	private static int STG2_TW_MS_r = 7;
	private static int STG2_TW_MS_R = 8;
	private static int STG2_TW_MS_f = 9;
	private static int STG2_TW_MS_F = 10;
	private static int STG2_TW_MS_b = 11;
	private static int STG2_TW_MS_B = 12;

	private static int stage2_slice_moves_to_try [] = {
	0xFFFFFFF,
	0xFFFFFF8, 0xFFFFFC0, 0xFFFF1C0, 0xFFFF000,
	0xFF60FFF, 0xFFF1FFF, 0xFF10FFF, 0xFF00FFF,
	0x60FFFFF, 0xF1FFFFF, 0x10FFFFF, 0x00FFFFF
	};

	private static int stage2_stm_next_ms[] = 	{ SL_MS_U,SL_MS_U,SL_MS_U,SL_MS_u,SL_MS_u,SL_MS_u,SL_MS_D,SL_MS_D,SL_MS_D,SL_MS_d,SL_MS_d,SL_MS_d,SL_MS_L,SL_MS_l,SL_MS_l,SL_MS_l,SL_MS_R,SL_MS_r,SL_MS_r,SL_MS_r,SL_MS_F,SL_MS_f,SL_MS_f,SL_MS_f,SL_MS_B,SL_MS_b,SL_MS_b,SL_MS_b };

	private static int stage2_2twist_types[] = {
	22, 22, 23, 23, 22, 22, 23, 23,
	20, 20, 21, 21, 20, 20, 21, 21
};

	private static int stage2_twist_moves_to_try [] = {
	0xFFFFFF,
	0xFFFE3F, 0xFFFE38, 0xFFF038, 0xFFF000,
	0xEFBFFF, 0xEFAFFF, 0xCF2FFF, 0xCF0FFF,
	0xBBFFFF, 0xBAFFFF, 0x32FFFF, 0x30FFFF
};

	private static int stage2_twist_next_ms[][] = {
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },

	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },

	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_X,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_X,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_X,STG2_TW_MS_R,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_X,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_F,STG2_TW_MS_B },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_f,STG2_TW_MS_b,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_F,STG2_TW_MS_B },

	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_F,STG2_TW_MS_B,STG2_TW_MS_X,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_B },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_X,STG2_TW_MS_B,STG2_TW_MS_X,STG2_TW_MS_b,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_B },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_X,STG2_TW_MS_B,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_X },
	{	STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_U,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_D,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_u,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_d,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_l,STG2_TW_MS_r,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_X,STG2_TW_MS_L,STG2_TW_MS_R,STG2_TW_MS_X,STG2_TW_MS_X }
};

	private static byte S2BMTT_X = 0;		//moves not used for IDA* search
	private static byte S2BMTT_UGEN = 1;	//U-axis general moves
	private static byte S2BMTT_u = 2;
	private static byte S2BMTT_u3 = 3;
	private static byte S2BMTT_u2 = 4;
	private static byte S2BMTT_D = 5;
	private static byte S2BMTT_D3 = 6;
	private static byte S2BMTT_D2 = 7;
	private static byte S2BMTT_d = 8;
	private static byte S2BMTT_d3 = 9;
	private static byte S2BMTT_d2 = 10;
	private static byte S2BMTT_u2d2 = 11;
	private static byte S2BMTT_ud3 = 12;	//and u3d
	private static byte S2BMTT_LGEN = 13;	//L-Axis general moves
	private static byte S2BMTT_l = 14;	//and l'
	private static byte S2BMTT_r = 15;
	private static byte S2BMTT_r3 = 16;
	private static byte S2BMTT_r2 = 17;
	private static byte S2BMTT_lr3 = 18;	//and l'r
	private static byte S2BMTT_FGEN = 19;	//F-Axis general moves
	private static byte S2BMTT_f = 20;	//and f'
	private static byte S2BMTT_b = 21;
	private static byte S2BMTT_b3 = 22;
	private static byte S2BMTT_b2 = 23;
	private static byte S2BMTT_fb3 = 24;	//and f'b

	private static byte stage2_btm_mtt_idx[] = {
	S2BMTT_UGEN, S2BMTT_UGEN, S2BMTT_UGEN,		//U, U', U2
	S2BMTT_u, S2BMTT_u3, S2BMTT_u2,				//u, u', u2
	S2BMTT_D, S2BMTT_D3, S2BMTT_D2,				//D, D', D2
	S2BMTT_d, S2BMTT_d3, S2BMTT_d2,				//d, d', d2
	S2BMTT_UGEN, S2BMTT_UGEN, S2BMTT_UGEN,		//(Uu), (Uu)', (Uu)2
	S2BMTT_X, S2BMTT_X, S2BMTT_X,				//(Dd), (Dd)', (Dd)2
	S2BMTT_ud3, S2BMTT_ud3, S2BMTT_u2d2,		//(ud'), (u'd), (ud')2
	S2BMTT_LGEN,								//L2
	S2BMTT_l, S2BMTT_l, S2BMTT_LGEN,			//l, l', l2
	S2BMTT_LGEN,								//R2
	S2BMTT_r, S2BMTT_r3, S2BMTT_r2,				//r, r', r2
	S2BMTT_LGEN, S2BMTT_X,						//(Ll)2, (Rr)2
	S2BMTT_lr3, S2BMTT_lr3, S2BMTT_LGEN,		//(lr'), (l'r), (lr')2
	S2BMTT_FGEN,								//F2
	S2BMTT_f, S2BMTT_f, S2BMTT_FGEN,			//f, f', f2
	S2BMTT_FGEN,								//B2
	S2BMTT_b, S2BMTT_b3, S2BMTT_b2,				//b, b', b2
	S2BMTT_FGEN, S2BMTT_X,						//(Ff)2, (Bb)2
	S2BMTT_fb3, S2BMTT_fb3, S2BMTT_FGEN			//(fb'), (f'b), (fb')2
};

	private static int stage2_block_moves_to_try [] = {
	(0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_u3) | (1 << S2BMTT_u2) | (1 << S2BMTT_D) | (1 << S2BMTT_D2) | (1 << S2BMTT_d) | (1 << S2BMTT_d3) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_u) | (1 << S2BMTT_u2) | (1 << S2BMTT_D3) | (1 << S2BMTT_D2) | (1 << S2BMTT_d) | (1 << S2BMTT_d3) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_u) | (1 << S2BMTT_u3) | (1 << S2BMTT_D) | (1 << S2BMTT_D3) | (1 << S2BMTT_d) | (1 << S2BMTT_d3) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_D) | (1 << S2BMTT_D2) | (1 << S2BMTT_d) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_D3) | (1 << S2BMTT_D2) | (1 << S2BMTT_d3) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_D) | (1 << S2BMTT_D3) | (1 << S2BMTT_d) | (1 << S2BMTT_d3)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_d3) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_d) | (1 << S2BMTT_d2)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_d) | (1 << S2BMTT_d3)) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	(1 << S2BMTT_u2d2) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	(1 << S2BMTT_ud3) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	(1 << S2BMTT_D3) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	(1 << S2BMTT_D) | (0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	(0x3F << S2BMTT_LGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_l) | ((1 << S2BMTT_r) | (1 << S2BMTT_r3)) | (1 << S2BMTT_r2) | ((1 << S2BMTT_lr3))) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_l) | ((1 << S2BMTT_r) | (1 << S2BMTT_r3))) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_r) | (1 << S2BMTT_r2)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_r3) | (1 << S2BMTT_r2)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_r) | (1 << S2BMTT_r3)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	(1 << S2BMTT_lr3) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	(0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_FGEN),
	((1 << S2BMTT_f) | ((1 << S2BMTT_b) | (1 << S2BMTT_b3)) | (1 << S2BMTT_b2) | ((1 << S2BMTT_fb3))) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	((1 << S2BMTT_f) | ((1 << S2BMTT_b) | (1 << S2BMTT_b3))) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	((1 << S2BMTT_b) | (1 << S2BMTT_b2)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	((1 << S2BMTT_b3) | (1 << S2BMTT_b2)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	((1 << S2BMTT_b) | (1 << S2BMTT_b3)) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	(1 << S2BMTT_fb3) | (0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN),
	(0xFFF << S2BMTT_UGEN) | (0x3F << S2BMTT_LGEN)
};

	private static int stage2_btm_next_ms[][] = {
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,13,12,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 21,21,21,21,21,21,21,21,21,0,21,21,21, 22,24,25,26,23,28,28,28,27,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 },
	{ 1,2,3,4,5,6,7,8,9,14,14,14,10,10,11,0,0,0,14,14,14, 15,17,18,19,16,21,21,21,20,0,21,21,21, 28,28,28,28,28,28,28,28,28,0,28,28,28 }
};

	private CubeStage2 cube = new CubeStage2();

	Stage2Solver( PipedInputStream pipeIn, PipedOutputStream pipeOut ) throws java.io.IOException{
		super( pipeIn, pipeOut );

		stage_slice_list = new byte[]{
		Uf, Uf3, Uf2, Us, Us3, Us2,
		Df, Df3, Df2, Ds, Ds3, Ds2,
		Lf2, Ls, Ls3, Ls2, Rf2, Rs, Rs3, Rs2,
		Ff2, Fs, Fs3, Fs2, Bf2, Bs, Bs3, Bs2
		};
	}

	void importState(){
		ss.cube.convert_to_stage2 (cube);
	}

	int id;
	int best;

	public void run (){
		id = 0;

		while(pullState()) {
			if( id != ss.id ){
				id = ss.id;
				best = 100;
			}
			/*
			int cubeDist = cube.prune_funcEDGCEN_STAGE2();
			if( cubeDist > ( best - ss.move_count ))
				continue;
			*/
			foundSol = false;
			//for (goal = 0; goal < ( best - ss.move_count ); ++goal) {
			for (goal = 0; goal < 30; ++goal) {
				treeSearch (cube, goal, 0, 0);
				if (foundSol){
					best = ss.move_count + goal;
					break;
				}
			}
		}

		pushStopSignal();
		closePipes();
	}

	public boolean treeSearch (CubeStage2 cube1, int depth, int moves_done, int move_state){
		//Statistics.addNode(2, depth);
		CubeStage2 cube2 = new CubeStage2();
		int mov_idx, mc, j;
		int next_ms = 0;
		if (depth == 0) {
			if (! cube1.is_solved ()) {
				return false;
			}
			pushState();
			Statistics.addLeaf(2, goal);
			return true; // true: take the first solution, false: take all solutions.
		}
		if (metric == 1 && depth >= 2) { // TODO: maybe put this on bottom of the search tree...
			for (mov_idx = 0; mov_idx < N_STAGE2_2TWIST_MOVES; ++mov_idx) {
				int mtype = stage2_2twist_types[mov_idx];
				if ((stage2_twist_moves_to_try[move_state] & (1 << mtype)) != 0) {
					cube2.m_edge = cube1.m_edge;
					cube2.m_centerFB = cube1.m_centerFB;

					for (j = 0; stage2_2twist_moves[mov_idx][j] >= 0; ++j) {
						mc = stage2_2twist_moves[mov_idx][j];		//!!! metric dependency
						cube2.do_move (mc);		//!!! metric dependency
					}
					if (cube2.prune_funcEDGCEN_STAGE2() > depth-1) continue;
					next_ms = stage2_twist_next_ms[move_state][mtype];
					move_list[moves_done] = (byte)stage2_twist_map1[Constants.N_STAGE2_TWIST_MOVES + mov_idx];
					move_list[moves_done + 1] = (byte)stage2_twist_map2[Constants.N_STAGE2_TWIST_MOVES + mov_idx];
					if (treeSearch (cube2, depth - 2, moves_done + 2, next_ms)) return true;
				}
			}
		}
		for (mov_idx = 0; mov_idx < n_moves_metric_stg2[metric]; ++mov_idx) {
			boolean did_move = false;
			switch (metric) {
			case 0:
				if ((stage2_slice_moves_to_try[move_state] & (1 << mov_idx)) != 0) {
					cube2.m_edge = cube1.m_edge;
					cube2.m_centerFB = cube1.m_centerFB;
					cube2.do_move (mov_idx);
					next_ms = stage2_stm_next_ms[mov_idx];
					did_move = true;
				}
				break;
			case 1:
				if ((stage2_twist_moves_to_try[move_state] & (1 << mov_idx)) != 0) {
					cube2.m_edge = cube1.m_edge;
					cube2.m_centerFB = cube1.m_centerFB;
					for (j = 0; stage2_twist_moves[mov_idx][j] >= 0; ++j) {
						mc = stage2_twist_moves[mov_idx][j];		//!!! metric dependency
						cube2.do_move (mc);		//!!! metric dependency
					}
					next_ms = stage2_twist_next_ms[move_state][mov_idx];
					did_move = true;
				}
				break;
			case 2:
				if ((stage2_block_moves_to_try[move_state] & (1 << stage2_btm_mtt_idx[mov_idx])) != 0) {
					cube2.m_edge = cube1.m_edge;
					cube2.m_centerFB = cube1.m_centerFB;
					for (j = 0; stage2_block_moves[mov_idx][j] >= 0; ++j) {
						mc = stage2_block_moves[mov_idx][j];		//!!! metric dependency
						cube2.do_move (mc);		//!!! metric dependency
					}
					next_ms = stage2_btm_next_ms[move_state][mov_idx];
					did_move = true;
				}
				break;
			}
			if (did_move) {
				if (cube2.prune_funcEDGCEN_STAGE2() > depth-1) continue;
				mc = mov_idx;
				switch (metric) {
				case 1:
					mc = stage2_twist_map1[mov_idx];
					break;
				case 2:
					mc = stage2_block_map[mov_idx];
					break;
				}
				move_list[moves_done] = (byte)mc;
				if (treeSearch (cube2, depth - 1, moves_done + 1, next_ms)) return true;
			}
		}
		return false;
	}

	int rotateCube(CubeState cube, byte[] sol_move_list){
		int i;
		for (i = 0; i < goal; ++i) {
			sol_move_list[i] = xlate_r6[sol_move_list[i]][ss.rotate];
		}
		int r6 = ss.rotate;
		if (cube.m_cen[16] < 4) {
			cube.do_move (Uf);
			cube.do_move (Us);
			cube.do_move (Ds3);
			cube.do_move (Df3);
			r6 += 3;
		}
		return r6;
	}

}
